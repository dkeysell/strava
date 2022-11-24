package com.djk.komhunter.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class XomsDTO {
	private String kom;
	private String qom;
	public String getKom() {
		return kom;
	}
	public void setKom(String kom) {
		this.kom = kom;
	}
	public String getQom() {
		return qom;
	}
	public void setQom(String qom) {
		this.qom = qom;
	}
	
	

}
