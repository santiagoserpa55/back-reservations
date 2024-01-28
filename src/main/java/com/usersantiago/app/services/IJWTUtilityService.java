package com.usersantiago.app.services;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;

public interface IJWTUtilityService {
	public String generateJWT(Integer userId)
			throws InvalidKeySpecException, NoSuchAlgorithmException, IOException, JOSEException;

	public JWTClaimsSet parseJWT(String jwt)
			throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, ParseException, JOSEException;

}
