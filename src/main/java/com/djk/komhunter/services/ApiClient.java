package com.djk.komhunter.services;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.djk.komhunter.dtos.TokenDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ApiClient {
	@Autowired
	private OauthLogin oauthLogin;
	
	@Value("${token.filepath}")
	private String tokenFilePath;
	
	Logger logger = LoggerFactory.getLogger(ApiClient.class);
	
	private Long expiryTime = null;
	
	private String accessToken;
	
	private String refreshToken;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public <T> ResponseEntity<T> getRequest(String url, Class<T> type) throws Exception {
		logger.debug("Grid explore API getting called {}", url);
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.exchange(
				url, 
				HttpMethod.GET,
				httpEntity(),
				type);
	}

	private HttpEntity<Map<String, List<String>>> httpEntity() throws Exception {
		if(expiryTime == null) {
			readTokenFile();
		}
		if(System.currentTimeMillis() > expiryTime) {
			oauthLogin.refreshToken(refreshToken);
			readTokenFile();
		}
		MultiValueMap<String, String> headerMap = new HttpHeaders();
		headerMap.put("authorization",  Collections.singletonList(String.format("Bearer %s", accessToken)));
		return new HttpEntity<>(headerMap);
	}

	private void readTokenFile() throws Exception {
		File tokenFile = new File(tokenFilePath);
		TokenDTO token = objectMapper.readValue(tokenFile, TokenDTO.class);
		expiryTime = token.getExpiresAt();
		accessToken = token.getAccessToken();
		refreshToken = token.getRefreshToken();
	}

}
