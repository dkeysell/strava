package com.djk.komhunter.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.djk.komhunter.dtos.ExploreStatusDTO;
import com.djk.komhunter.dtos.SegmentRetrieveStatusDTO;
import com.djk.komhunter.models.Grid;
import com.djk.komhunter.models.Segment;
import com.djk.komhunter.repositories.GridRepository;
import com.djk.komhunter.repositories.SegmentRepository;
import com.djk.komhunter.services.SegmentExplorer;

@RestController
@RequestMapping("api/v1/grid")
public class GridController {
	
	@Autowired
	private GridRepository gridRepository;
	
	@Autowired
	private SegmentRepository segmentRepository;
	
	@Autowired
	private SegmentExplorer segmentExplorer;
	
	Logger logger = LoggerFactory.getLogger(GridController.class);
	
	@GetMapping
	public List<Grid> list(){
		return gridRepository.findAll();
	}
	
	@GetMapping
	@RequestMapping("{id}")
	public Grid get(@PathVariable Long id) {
		return gridRepository.findById(id).get();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Grid create(@RequestBody final Grid grid) {
		return gridRepository.saveAndFlush(grid);
	}
	
	@DeleteMapping
	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		gridRepository.deleteById(id);
	}
	
	@PutMapping
	@RequestMapping(value="{id}", method = RequestMethod.PUT)
	public Grid update(@PathVariable Long id, @RequestBody Grid grid) {
		Grid existingGrid = gridRepository.getReferenceById(id);
		BeanUtils.copyProperties(grid, existingGrid, "id");
		return gridRepository.saveAndFlush(existingGrid);
	}
	
	@GetMapping
	@RequestMapping("/explore")
	public ResponseEntity<ExploreStatusDTO> explore(@RequestParam float swLat, @RequestParam float swLong, @RequestParam int size){
		
		try {
		    return ResponseEntity.ok(segmentExplorer.retrieveGrids(swLat, swLong, size));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping
	@RequestMapping("/discover")
	public ResponseEntity<List<Segment>> discover(@RequestParam float swLat, @RequestParam float swLong, @RequestParam int size){
		try {
		ExploreStatusDTO exploreStatusDTO = segmentExplorer.retrieveGrids(swLat, swLong, size);
		List<Segment> segments = segmentRepository.findAllById(exploreStatusDTO.getSegmentIds());
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Access-Control-Allow-Origin", "*");
		responseHeaders.add("Referrer-Policy", "no-referrer");
		return new ResponseEntity(segments, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	@GetMapping
	@RequestMapping("/retrieve_segments") 
	public ResponseEntity<SegmentRetrieveStatusDTO> retrieveSegments() {
		try {
			return ResponseEntity.ok(segmentExplorer.retrieveSegments());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
