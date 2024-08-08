package com.dd.bikes.jwt;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
	private static final Logger jwtLogger = LogManager.getLogger(JWTFilterConfiguration.class);
//	Checks the filter when sending the authentication

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		extractTokenFromRequestHeader(request).map(jwtConfig::decodeToken).map(jwtConfig::parseJWT)
				.map(JWTUserAuthentication::new).ifPresent(authentication -> {
					SecurityContextHolder.getContext().setAuthentication(authentication);
					// Print username and roles
					logUserDetails(authentication);
				});
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

	// Log user details if authentication is valid
	private void logUserDetails(Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			String roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", "));
			// Log user name and roles
			jwtLogger.info("Authenticated User: {} Having roles are : {} ", username,roles);
		}
	}

}
