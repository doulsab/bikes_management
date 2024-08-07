package com.dd.bikes.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties("security.jwt")
@Setter
@Getter
public class JwtKeyResponse {
	private String secreteKey;
}
