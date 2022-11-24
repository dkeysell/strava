package com.djk.komhunter.dtos;

import java.util.List;

import com.djk.komhunter.models.Segment;

public class ExploreDTO {
	
	private List<Segment> segments;

	public List<Segment> getSegments() {
		return segments;
	}

	public void setSegments(List<Segment> segments) {
		this.segments = segments;
	}

}
