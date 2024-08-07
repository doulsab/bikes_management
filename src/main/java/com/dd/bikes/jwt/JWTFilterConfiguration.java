package com.dd.bikes.jwt;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor

public class JWTFilterConfiguration extends OncePerRequestFilter {

	private final JWTTokenConfig jwtConfig;
	
//	Checks the filter when sending the authentication

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		extractTokenFromRequestHeader(request)
		.map(jwtConfig::decodeToken)
		.map(jwtConfig::parseJWT)
		.map(JWTUserAuthentication::new)
		.ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));
		filterChain.doFilter(request, response);
	}

//	Extract the token from HTTP request

	private Optional<String> extractTokenFromRequestHeader(HttpServletRequest headerRequest) {
		var token = headerRequest.getHeader("Authorization");
		if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
			return Optional.of(token.substring(7));
		}
		return Optional.empty();
	}

}
