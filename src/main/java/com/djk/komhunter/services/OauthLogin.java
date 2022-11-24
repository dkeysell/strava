package com.djk.komhunter.services;

import java.net.URI;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.djk.komhunter.dtos.TokenDTO;
import com.djk.komhunter.dtos.TokenRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OauthLogin {
	
	@Value("${token.clientId}")
	private String clientId;
	
	@Value("${token.clientSecret}")
	private String clientSecret;
	
	@Value("${token.filepath}")
	private String tokenFilePath;

	public void refreshToken(String refreshToken) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		
		TokenDTO response = restTemplate.postForObject(new URI("https://www.strava.com/oauth/token"), 
				                   new TokenRequestDTO(clientId, clientSecret, "refresh_token", refreshToken), 
				                   TokenDTO.class);
		
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(Paths.get(tokenFilePath).toFile(), response);
			
		
		
		
		
	}

}
