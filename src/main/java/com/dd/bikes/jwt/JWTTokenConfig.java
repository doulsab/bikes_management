package com.dd.bikes.jwt;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dd.bikes.model.UserPricipal;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor

public class JWTTokenConfig {

	private final JwtKeyResponse jwtResponse;

	public String createToken(String username, String email, Collection<? extends GrantedAuthority> authorities) {
		// Convert authorities to a list of strings
		List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).toList();

		return JWT.create().withIssuer(jwtResponse.getIssuer()).withSubject(username).withClaim("email", email)
				// 1 hour expiration
				.withClaim("roles", roles).withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
				.sign(Algorithm.HMAC256(jwtResponse.getSecreteKey()));
	}

	public DecodedJWT decodeToken(String token) {
		return JWT.require(Algorithm.HMAC256(jwtResponse.getSecreteKey())).build().verify(token);
	}

	public UserPricipal parseJWT(DecodedJWT jwt) {
		return UserPricipal.builder().username(jwt.getSubject()).email(jwt.getClaim("email").asString())
				.authorities(getAuthorityByJwt(jwt)).build();
	}

	private List<SimpleGrantedAuthority> getAuthorityByJwt(DecodedJWT jwt) {
		var roles = jwt.getClaim("roles");
		if (roles.isNull() || roles.isMissing())
			return List.of();
		return roles.asList(SimpleGrantedAuthority.class);

	}

}
