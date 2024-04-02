package com.usersantiago.app.services.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.usersantiago.app.services.IJWTUtilityService;

@Service
public class JWTUtilityServiceImpl implements IJWTUtilityService {
	
	static final String MSG_INVALID_SIGNATURE = "Invalid Signature";
	static final String MSG_EXPIRED_TOKEN = "Expired token";

	// añadimos los @Value, pasar el path de las claves
	@Value("classpath:jwtKeys/private_key.pem")
	private Resource privateKeyResource;

	@Value("classpath:jwtKeys/public_key.pem")
	private Resource publicKeyResource;

	@Override
	public String generateJWT(Integer userId)
			throws InvalidKeySpecException, NoSuchAlgorithmException, IOException, JOSEException {
		PrivateKey privateKey = loadPrivateKey(privateKeyResource);

		JWSSigner signer = new RSASSASigner(privateKey);

		Date now = new Date();
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder().subject(userId.toString()).issueTime(now)
				.expirationTime(new Date(now.getTime() + 14400000)).build();
		SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claimsSet);
		signedJWT.sign(signer);
		return signedJWT.serialize();
	}

	// lectura para validar jwt que nos pasan
	@Override
	public JWTClaimsSet parseJWT(String jwt)
			throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, ParseException, JOSEException {
		PublicKey publicKey = loadPublicKey(publicKeyResource);

		SignedJWT signedJWT = SignedJWT.parse(jwt);

		JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);

		if (!signedJWT.verify(verifier)) {
			throw new JOSEException(MSG_INVALID_SIGNATURE);
		}

		JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

		LocalDateTime currentDateTime = LocalDateTime.now();

		// Convertir la fecha de expiración del token (Date) a LocalDateTime
		Date expirationDate = claimsSet.getExpirationTime();
		LocalDateTime expirationDateTime = expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

		// Comparar las fechas
		if (expirationDateTime.isBefore(currentDateTime)) {
		    throw new JOSEException(MSG_EXPIRED_TOKEN);
		}
		
		return claimsSet;
	}

	private PrivateKey loadPrivateKey(Resource resource)
			throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
		byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getURI()));
		String privateKeyPEM = new String(keyBytes, StandardCharsets.UTF_8).replace("-----BEGIN PRIVATE KEY-----", "")
				.replace("-----END PRIVATE KEY-----", "").replaceAll("\\s", "");
		byte[] decodeKey = Base64.getDecoder().decode(privateKeyPEM);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodeKey));
	}

	private PublicKey loadPublicKey(Resource resource)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getURI()));
		String publicKeyPEM = new String(keyBytes, StandardCharsets.UTF_8).replace("-----BEGIN PUBLIC KEY-----", "")
				.replace("-----END PUBLIC KEY-----", "").replaceAll("\\s", "");
		byte[] decodeKey = Base64.getDecoder().decode(publicKeyPEM);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(new X509EncodedKeySpec(decodeKey));

	}

}
