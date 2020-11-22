package com.jwt.models;

public class AuthenticationResponse {
	private final String jwtResponse;

	public String getJwtResponse() {
		return jwtResponse;
	}

	public AuthenticationResponse(String jwtResponse) {
		super();
		this.jwtResponse = jwtResponse;
	}	
}
