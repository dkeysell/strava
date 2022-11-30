package com.djk.komhunter.dtos;

public class SegmentRetrieveStatusDTO {
	
	
	private String status;
	private Integer gridHandledCount;
	private Integer segmentHandledSuccessCount;
	private Integer segmentHandledFailureCount;
	
	public SegmentRetrieveStatusDTO(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public Integer getSegmentHandledSuccessCount() {
		return segmentHandledSuccessCount;
	}

	public void setSegmentHandledSuccessCount(Integer segmentHandledSuccessCount) {
		this.segmentHandledSuccessCount = segmentHandledSuccessCount;
	}

	public Integer getSegmentHandledFailureCount() {
		return segmentHandledFailureCount;
	}

	public void setSegmentHandledFailureCount(Integer segmentHandledFailureCount) {
		this.segmentHandledFailureCount = segmentHandledFailureCount;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getGridHandledCount() {
		return gridHandledCount;
	}

	public void setGridHandledCount(Integer gridHandledCount) {
		this.gridHandledCount = gridHandledCount;
	}

	

	

}
