package com.djk.komhunter.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name="grid")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Grid {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="sw_lat")
	private Float swLat;
	
	@Column(name="sw_long")
	private Float swLong;
	
	
	@OneToMany(mappedBy = "grid", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Segment> segments = new ArrayList<>();
	
	public Grid(Float swLat, Float swLong, List<Segment> segments) {
		this.swLat = swLat;
		this.swLong = swLong;
		this.segments = segments;
	}
	
	

	public List<Segment> getSegments() {
		return segments;
	}

	public void setSegments(List<Segment> segments) {
		this.segments = segments;
	}

	public Grid() {	
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getSwLat() {
		return swLat;
	}

	public void setSwLat(Float swLat) {
		this.swLat = swLat;
	}

	public Float getSwLong() {
		return swLong;
	}

	public void setSwLong(Float swLong) {
		this.swLong = swLong;
	}

}
