package com.djk.komhunter.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.djk.komhunter.dtos.ExploreDTO;
import com.djk.komhunter.dtos.ExploreStatusDTO;
import com.djk.komhunter.dtos.SegmentRetrieveStatusDTO;
import com.djk.komhunter.models.Grid;
import com.djk.komhunter.models.Segment;
import com.djk.komhunter.repositories.GridRepository;
import com.djk.komhunter.repositories.SegmentRepository;
import com.djk.komhunter.utils.BeanCopyUtils;
import com.djk.komhunter.utils.WattCalculator;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SegmentExplorer {
	
	@Autowired
	private ApiClient apiClient;
	
	@Autowired
	private GridRepository gridRepository;
	
	@Autowired
	private SegmentRepository segmentRepository;
	
	@Value("${grid.directory.tmp}")
	private String gridTmpDirectoryPath;
	
	@Value("${grid.directory.longterm}")
	private String gridLongTermDirectoryPath;
	
	@Value("${segment.directory.tmp}")
	private String segmentTmpDirectoryPath;
	
	private float increment = 0.01f;
	
	private Logger logger = LoggerFactory.getLogger(SegmentExplorer.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	// https://www.strava.com/api/v3/segments/explore?bounds={sw_lat},{sw_long},{ne_lat},{ne_long}&activity_type=riding
	private static final String EXPLORE_URL_TEMPLATE = "https://www.strava.com/api/v3/segments/explore?bounds=%f,%f,%f,%f&activity_type=riding";
	
	private static final String SEGMENT_URL_TEMPLATE = "https://www.strava.com/api/v3/segments/%d";
	
	private static final String GRID_FILE_TEMPLATE = "%s%fto%f.json";
	
	private static final String SEGMENT_FILE_TEMPLATE = "%s%d.json";
	
	public ExploreStatusDTO retrieveGrids(float swLat, float swLong, int size) throws Exception {
		int longCount = 0;
		int latCount = 0;
		
		float swLongOrigin = swLong;
		int requestCount = 0;
		int segmentCount = 0;
		List<Long> segmentIds = new ArrayList<>();
		ExploreStatusDTO status = new ExploreStatusDTO("Success");
		
		while(latCount < size) {
			swLat = Math.round(swLat * 100)/ 100f;
			swLong = Math.round(swLong * 100)/ 100f;
			logger.info("Grid coordinates: {}-{}", swLat, swLong);
			
			Example<Grid> ex = Example.of(new Grid(swLat, swLong, null));			
			Optional<Grid> dbGridOptional = gridRepository.findOne(ex);
			if(dbGridOptional.isPresent()) {
				requestCount++;
				longCount++;
				List<Segment> segments = dbGridOptional.get().getSegments();
				if(segments != null) {
					segmentCount = segmentCount + segments.size();
					for(Segment segment : segments) {
						segmentIds.add(segment.getId());
					}
				}
				if(longCount < size) {
					swLong += increment;
				} else {
					longCount = 0;
					latCount = latCount + 1;
					swLong = swLongOrigin;
					swLat = swLat - increment;
				}
				continue;
			}
			float neLat = swLat + increment;
			float neLong = swLong + increment;
			requestCount++;
			ResponseEntity<ExploreDTO> exploreResponse = apiClient.getRequest(String.format(EXPLORE_URL_TEMPLATE, swLat, swLong, neLat, neLong), ExploreDTO.class);
			if(exploreResponse.getStatusCodeValue() == 200) {
				longCount++;
				
				ExploreDTO exploreDTO = exploreResponse.getBody();
				if(exploreDTO != null) {
					Path tmpGridFilePath = Paths.get(String.format(GRID_FILE_TEMPLATE, gridTmpDirectoryPath, swLat, swLong));
					mapper.writeValue(tmpGridFilePath.toFile(), exploreDTO);
					persistGrid(swLat, swLong, exploreDTO);
					if(retrieveFullSegments(exploreDTO)) {
						Files.move(tmpGridFilePath, Path.of(gridLongTermDirectoryPath, tmpGridFilePath.getFileName().toString()), StandardCopyOption.REPLACE_EXISTING);
					}
					if(exploreDTO.getSegments() != null) {
						segmentCount = segmentCount + exploreDTO.getSegments().size();
						for(Segment segment : exploreDTO.getSegments()) {
							segmentIds.add(segment.getId());
						}
					}
				}
				
				if(longCount < size) {
					swLong += increment;
				} else {
					longCount = 0;
					latCount = latCount + 1;
					swLong = swLongOrigin;
					swLat = swLat - increment;
				}
			} else {
				String message = String.format("Error retrieving lat: %f long: %f, http status code: %i", swLat, swLong, exploreResponse.getStatusCode());
				logger.error(message);
				status.setStatusMessage(message);
				break;
			}
		}
		status.setRequestCount(requestCount);
		status.setSegmentCount(segmentCount);
		status.setSegmentsPerRequest(segmentCount/requestCount);
		status.setSegmentIds(segmentIds);
		return status; 
	}

	private boolean retrieveFullSegments(ExploreDTO exploreDTO) throws Exception {
		boolean success = true;
		if(exploreDTO.getSegments() == null) {
			return success;
		}
		for(Segment segment : exploreDTO.getSegments()) {
			Optional<Segment> optionalSegment = retrieveSegment(segment);
			if(optionalSegment.isPresent()) {
				persistFullSegment(segment, optionalSegment.get());
	            
			} else {
				logger.error("Unable to retrieve segment: {}", segment.getId());
				success = false;
			}
		}
		return success;
	}

	private void persistFullSegment(Segment dbSegment, Segment fullSegment) {
		BeanUtils.copyProperties(fullSegment, dbSegment, BeanCopyUtils.getNullPropertyNames(fullSegment));
		segmentRepository.saveAndFlush(dbSegment);
	}

	private void persistGrid(float swLat, float swLong, ExploreDTO exploreDTO) {		
		Grid grid = new Grid(swLat, swLong, exploreDTO.getSegments());
		if(exploreDTO.getSegments() != null) {
			exploreDTO.getSegments().forEach(segment -> segment.setGrid(grid));
		    List<Segment> newSegmentsOnly = exploreDTO.getSegments().stream().filter(segment -> !segmentRepository.existsById(segment.getId())).
		                                     collect(Collectors.toList());
		    grid.setSegments(newSegmentsOnly);
		}
		
		gridRepository.saveAndFlush(grid);
	}

	public SegmentRetrieveStatusDTO retrieveSegments() throws Exception {
		int gridHandledCount = 0;
		int segmentHandledSuccessCount = 0;
		int segmentHandledFailureCount = 0;
		SegmentRetrieveStatusDTO status = new SegmentRetrieveStatusDTO("Success");
		
		for(File gridFile : new File(gridTmpDirectoryPath).listFiles()) {
			gridHandledCount++;
			boolean canMoveGridFile = true;
			logger.info("Processing file: {}", gridFile.getName());
			ExploreDTO exploreDto = mapper.readValue(gridFile, ExploreDTO.class);
			for(Segment segment : exploreDto.getSegments()) {
				if(retrieveSegment(segment).isPresent()) {
					segmentHandledSuccessCount++;
				} else {
					segmentHandledFailureCount++;
					canMoveGridFile = false;
				}
			}
			if(canMoveGridFile) {
				Files.move(gridFile.toPath(), Path.of(gridLongTermDirectoryPath, gridFile.getName()), StandardCopyOption.REPLACE_EXISTING);
			}
			
		}
		
		status.setGridHandledCount(gridHandledCount);
		status.setSegmentHandledSuccessCount(segmentHandledSuccessCount);
		status.setSegmentHandledFailureCount(segmentHandledFailureCount);
		return status;
	}
	
	private Optional<Segment> retrieveSegment(Segment segment) throws Exception {
		ResponseEntity<Segment> segmentResponse = apiClient.getRequest(String.format(SEGMENT_URL_TEMPLATE, segment.getId()), Segment.class);
		
		if(segmentResponse.getStatusCode() == HttpStatus.OK){
			
			Segment fullSegment = segmentResponse.getBody();
			if(fullSegment != null) {
				mapper.writeValue(Paths.get(String.format(SEGMENT_FILE_TEMPLATE, segmentTmpDirectoryPath, fullSegment.getId())).toFile(), fullSegment);
				fullSegment.setKomWatts(WattCalculator.calculateWatts(fullSegment.getKom(), fullSegment.getDistance(), fullSegment.getAverageGrade()));
				fullSegment.setQomWatts(WattCalculator.calculateWatts(fullSegment.getQom(), fullSegment.getDistance(), fullSegment.getAverageGrade()));
				
				return Optional.of(fullSegment);
			}
			
		}
		return Optional.empty();
	}

}
