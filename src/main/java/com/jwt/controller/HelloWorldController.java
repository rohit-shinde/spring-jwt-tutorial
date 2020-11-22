package com.jwt.controller;

import java.net.PasswordAuthentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.interceptor.LoginInterceptor;
import com.jwt.models.AuthenticationRequest;
import com.jwt.models.AuthenticationResponse;
import com.jwt.service.MyUserDetailsService;
import com.jwt.utils.JwtUtils;

@RestController
public class HelloWorldController {
	
	private static final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@RequestMapping(value = "/hello",method = RequestMethod.GET)
	public String getHelloWorld() {
		logger.info("Hello API is called");
		return "Hello World";
	}
	
	@RequestMapping(value = "/authenicate",method = RequestMethod.POST)
	public ResponseEntity<?> getBearerToken(@RequestBody AuthenticationRequest request) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
		}catch(BadCredentialsException e){
			throw new Exception("Incorrect username or password");
		}
		
		final UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getUsername());
		final String jwt = jwtUtils.generateToken(userDetails);
		logger.info("Bearer token is {}",jwt);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
}
