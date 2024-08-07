package com.dd.bikes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//	Create Bean for SecurityChainFilter

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests
						// Allow access to static resources
						.requestMatchers("/css/**", "/js/**", "/images/**", "/app/**", "/vendor/**").permitAll()
						.requestMatchers("/", "/index", "/login","/authenticate").permitAll() // Allow access to public endpoints
						.anyRequest().authenticated() // Require authentication for all other endpoints

				).formLogin(formLogin -> formLogin.disable());

		return http.build();
	}

}
