package com.djk.komhunter.dtos;

import java.util.List;

public class ExploreStatusDTO {
	
	private int requestCount;
	private int segmentCount;
	private float segmentsPerRequest;
	private String statusMessage;
	private List<Long> segmentIds;
	
	public ExploreStatusDTO(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	public int getRequestCount() {
		return requestCount;
	}
	public void setRequestCount(int requestCount) {
		this.requestCount = requestCount;
	}
	public int getSegmentCount() {
		return segmentCount;
	}
	public void setSegmentCount(int segmentCount) {
		this.segmentCount = segmentCount;
	}
	public float getSegmentsPerRequest() {
		return segmentsPerRequest;
	}
	public void setSegmentsPerRequest(float segmentsPerRequest) {
		this.segmentsPerRequest = segmentsPerRequest;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public List<Long> getSegmentIds() {
		return segmentIds;
	}

	public void setSegmentIds(List<Long> segmentIds) {
		this.segmentIds = segmentIds;
	}

}
