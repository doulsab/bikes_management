package com.dd.bikes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dd.bikes.exception.CustomAuthenticationEntryPoint;
import com.dd.bikes.jwt.JWTFilterConfiguration;
import com.dd.bikes.model.CommonAttribute;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
	private final CommonAttribute commonAttribute;
	private final JWTFilterConfiguration jwtFilter;
	private final CustomAuthenticationEntryPoint customAuthEntryPoint;

//	Create Bean for SecurityChainFilter
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		Check for auth headers 
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		// Require authentication for all other endpoints
		http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests
						.requestMatchers(commonAttribute.publicUrls).permitAll().anyRequest().authenticated()
				).formLogin(AbstractHttpConfigurer::disable)
				.exceptionHandling(
						exceptionHandling -> exceptionHandling.authenticationEntryPoint(customAuthEntryPoint));
		return http.build();
	}

}
