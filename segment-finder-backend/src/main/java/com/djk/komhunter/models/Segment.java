package com.djk.komhunter.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.djk.komhunter.dtos.XomsDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name="segment")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Segment {
	
	@Transient
	private Logger logger = LoggerFactory.getLogger(Segment.class);
	
	@Id
	private long id;
	
	@JsonProperty(value="resource_state")
	@Column(name="resource_state")
	private int resourceState;	
	
	@ManyToOne
	@JoinColumn(name="grid_id", nullable=false)
	@JsonBackReference
	private Grid grid;
	
	private String name;
	
	@Column(name="activity_type")
	@JsonProperty(value="activity_type")
	private String activityType;
	
	@Column(name="climb_category")
	@JsonProperty(value="climb_category")
	private Integer climbCategory;
		

	private Float distance;
	
	@Column(name="average_grade")
	@JsonProperty(value="average_grade")
	private Float averageGrade;
	
	@Column(name="maximum_grade")
	@JsonProperty(value="maximum_grade")
	private Float maximumGrade;
	
	@Column(name="elevation_high")
	@JsonProperty(value="elevation_high")
	private Float elevationHigh;
	
	@Column(name="elevation_low")
	@JsonProperty(value="elevation_low")
	private Float elevationLow;

	@Transient
	@JsonProperty("start_latlng")
	private List<Float> startLatLong;
	
	@Column(name="startlat")
	private Float startLat;
	
	@Column(name="startlong")
	private Float startLong;
	
	@Transient
	@JsonProperty("end_latlng")
	private List<Float> endLatLong;
	
	@Column(name="endlat")
    private Float endLat;
	
	@Column(name="endlong")
	private Float endLong;
	
	private String city;
	
	private String state;
	
	private String country;
	
	@Column(name="private")
	@JsonProperty(value="private")
	private Boolean privateSegment;
	
	private Boolean hazardous;
	
	@Column(name="elevation_profile")
	@JsonProperty(value="elevation_profile")
	private String elevationProfile;
	
	@Column(name="effort_count")
	@JsonProperty(value="effort_count")
	private Integer effortCount;
	
	@Column(name="athlete_count")
	@JsonProperty(value="athlete_count")
	private Integer athleteCount;
	
	@Transient
	private XomsDTO xoms;

	private String kom;

	private String qom;
	
	private Float komWatts;
	
	private Float qomWatts;
	

	public Float getElevationHigh() {
		return elevationHigh;
	}

	public void setElevationHigh(Float elevationHigh) {
		this.elevationHigh = elevationHigh;
	}

	public Float getElevationLow() {
		return elevationLow;
	}

	public void setElevationLow(Float elevationLow) {
		this.elevationLow = elevationLow;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Boolean getPrivateSegment() {
		return privateSegment;
	}

	public void setPrivateSegment(Boolean privateSegment) {
		this.privateSegment = privateSegment;
	}

	public Boolean getHazardous() {
		return hazardous;
	}

	public void setHazardous(Boolean hazardous) {
		this.hazardous = hazardous;
	}

	public Integer getEffortCount() {
		return effortCount;
	}

	public void setEffortCount(Integer effortCount) {
		this.effortCount = effortCount;
	}

	public Integer getAthleteCount() {
		return athleteCount;
	}

	public void setAthleteCount(Integer athleteCount) {
		this.athleteCount = athleteCount;
	}

	public XomsDTO getXoms() {
		return xoms;
	}

	public void setXoms(XomsDTO xoms) {
		if(xoms != null) {
			this.setKom(xoms.getKom());
			this.setQom(xoms.getQom());
		}
		this.xoms = xoms;
	}
	
	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public Segment() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getResourceState() {
		return resourceState;
	}

	public void setResourceState(Integer resourceState) {
		this.resourceState = resourceState;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getClimbCategory() {
		return climbCategory;
	}

	public void setClimbCategory(Integer climbCategory) {
		this.climbCategory = climbCategory;
	}

	public Float getDistance() {
		return distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}

	public Float getAverageGrade() {
		return averageGrade;
	}

	public void setAverageGrade(Float averageGrade) {
		this.averageGrade = averageGrade;
	}
	
	public List<Float> getStartLatLong() {
		return startLatLong;
	}

	public void setStartLatLong(List<Float> startLatLong) {
		if(startLatLong.size() == 2) {
			setStartLat(startLatLong.get(0));
			setStartLong(startLatLong.get(1));
		} else {
			logger.warn("Segment {} start Lat Long not correct length: {}", id, startLatLong.size());
		}
		this.startLatLong = startLatLong;
	}
	
	public List<Float> getEndLatLong() {
		return endLatLong;
	}

	public void setEndLatLong(List<Float> endLatLong) {
		if(endLatLong.size() == 2) {
			setEndLat(endLatLong.get(0));
			setEndLong(endLatLong.get(1));
		} else {
			logger.warn("Segment {} end Lat Long not correct length: {}", id, startLatLong.size());
		}
		this.endLatLong = endLatLong;
	}
	
	
	public String getElevationProfile() {
		return elevationProfile;
	}

	public void setElevationProfile(String elevationProfile) {
		this.elevationProfile = elevationProfile;
	}
	
	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public Float getMaximumGrade() {
		return maximumGrade;
	}

	public void setMaximumGrade(Float maximumGrade) {
		this.maximumGrade = maximumGrade;
	}
	
	public Float getStartLat() {
		return startLat;
	}
	public void setStartLat(Float startLat) {
		this.startLat = startLat;
	}
	public Float getStartLong() {
		return startLong;
	}
	public void setStartLong(Float startLong) {
		this.startLong = startLong;
	}

	public Float getEndLat() {
		return endLat;
	}

	public void setEndLat(Float endLat) {
		this.endLat = endLat;
	}

	public Float getEndLong() {
		return endLong;
	}

	public void setEndLong(Float endLong) {
		this.endLong = endLong;
	}

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

	public Float getKomWatts() {
		return komWatts;
	}

	public void setKomWatts(Float watts) {
		this.komWatts = watts;
	}

	public Float getQomWatts() {
		return qomWatts;
	}

	public void setQomWatts(Float qomWatts) {
		this.qomWatts = qomWatts;
	}

}
