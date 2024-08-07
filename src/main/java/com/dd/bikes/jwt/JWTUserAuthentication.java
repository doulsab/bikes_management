package com.dd.bikes.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import com.dd.bikes.model.User;

public class JWTUserAuthentication extends AbstractAuthenticationToken {
	
	private static final long serialVersionUID = 6586733889601063516L;
	private final User principal;

	public JWTUserAuthentication(User principal) {
		super(principal.getAuthorities());
		this.principal = principal;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public User getPrincipal() {
		return principal;
	}

}
