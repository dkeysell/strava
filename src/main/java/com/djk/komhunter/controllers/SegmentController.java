package com.djk.komhunter.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.djk.komhunter.models.Segment;
import com.djk.komhunter.repositories.SegmentRepository;

@RestController
@RequestMapping("api/v1/segment")
public class SegmentController {
	
	@Autowired
	private SegmentRepository segmentRepository;
	
	@GetMapping
	public List<Segment> list(){
		return segmentRepository.findAll();
	}
	
	@GetMapping
	@RequestMapping(value="{id}")
	public Segment get(@PathVariable Long id) {
		return segmentRepository.findById(id).get();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Segment create(@RequestBody final Segment segment) {
		return segmentRepository.saveAndFlush(segment);
	}
	
	@DeleteMapping
	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		segmentRepository.deleteById(id);
	}
	
	@PutMapping
	@RequestMapping(value="{id}", method = RequestMethod.PUT)
	public Segment update(@PathVariable Long id, @RequestBody Segment segment) {
		Segment existingSegment = segmentRepository.getReferenceById(id);
		BeanUtils.copyProperties(segment, existingSegment, "id");
		return segmentRepository.saveAndFlush(existingSegment);
	}
	
	@GetMapping
	@RequestMapping(value="{process_segments}")
	public void processSegments() {
		
	}
	

}
