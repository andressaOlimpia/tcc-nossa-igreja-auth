package com.olimpia.tcc.nossaigrejaauthservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.util.Arrays;

@Configuration
public class AuthServerJwtConfig {
	
@Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
	    JwtAccessTokenConverter converter = new CustomJwtTokenConverter();
	    KeyStoreKeyFactory keyStoreKeyFactory = 
   
                    new KeyStoreKeyFactory(new ClassPathResource("andressa.jks"), "latitude-2022".toCharArray());
	   converter.setKeyPair(keyStoreKeyFactory.getKeyPair("andressa"));
	    return converter;
	}	
    
    @Bean
    public TokenEnhancer tokenEnhancer() {
    	TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(new JwtTokenEnhancer(), 
				accessTokenConverter()));
        return tokenEnhancerChain;
    }
}
