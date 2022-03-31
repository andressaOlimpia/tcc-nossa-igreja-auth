package com.olimpia.tcc.nossaigrejaauthservice.config;


import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CustomJwtTokenConverter extends JwtAccessTokenConverter {
	

	public static final long EXPIRATION = 86400; // 1 dia
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		String id = authentication.getName().split(",")[0];
		String username = authentication.getName().split(",")[1];
		String nome = authentication.getName().split(",")[2];
		String sobrenome = authentication.getName().split(",")[3];
                
		Map<String, Object> additionalInfo = new HashMap<>();
		additionalInfo.put("sobrenome", sobrenome);
		additionalInfo.put("nome", nome);
		additionalInfo.put("username", username);
		additionalInfo.put("id", id);
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

		// Encode Token to JWT
		String encoded = super.encode(accessToken, authentication);

		// Set JWT as value of the token
		((DefaultOAuth2AccessToken) accessToken).setValue(encoded);
		((DefaultOAuth2AccessToken) accessToken).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000));

		return accessToken;
	}

}