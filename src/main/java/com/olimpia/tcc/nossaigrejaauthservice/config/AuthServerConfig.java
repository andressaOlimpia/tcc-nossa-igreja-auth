package com.olimpia.tcc.nossaigrejaauthservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;

//TODO: Tentar tirar essas annotations aqui
@EntityScan(basePackages = {"com.olimpia.tcc.nossaigrejaauthservice.model"})
@EnableJpaRepositories({"com.olimpia.tcc.nossaigrejaauthservice.repository"})
@ComponentScan({"com.olimpia.tcc.nossaigrejaauthservice.service"})
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
	
    @Autowired
    @Qualifier("authenticationManagerBean")
    AuthenticationManager authenticationManager;
    
    @Autowired 
    TokenStore tokenStore;    
    
    @Autowired
    TokenEnhancer tokenEnhancer;

	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("client-password")
				.secret(passwordEncoder.encode("secret"))
				.authorizedGrantTypes("password")
				.scopes("oauth2")
				.autoApprove(true);
			
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore)
        	.tokenEnhancer(tokenEnhancer)
        	.authenticationManager(authenticationManager);
	}
}