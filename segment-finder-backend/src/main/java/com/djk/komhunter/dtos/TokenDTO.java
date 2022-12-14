package com.djk.komhunter.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenDTO {
	@JsonProperty(value="token_type")
	private String tokenType;
	
	@JsonProperty(value="access_token")
	private String accessToken;
	
	@JsonProperty(value="expires_at")
	private Long expiresAt;
	
	@JsonProperty(value="expires_in")
	private Integer expiresIn;
	
	@JsonProperty(value="refresh_token")
	private String refreshToken;
	
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public Long getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(Long expiresAt) {
		this.expiresAt = expiresAt;
	}
	public Integer getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
