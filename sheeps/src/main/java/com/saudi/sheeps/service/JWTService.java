package com.saudi.sheeps.service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudi.sheeps.dao.ErrorsDAO;
import com.saudi.sheeps.entity.BugsAndError;
import com.saudi.sheeps.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class JWTService  {
	@Autowired
	private ErrorsDAO errorsDAO ;
	
	public String createJWT(Map<String, Object> headers, Map<String, String> claims) throws BusinessException {
		try {
			log.info("Enter createJWT Function...");
			// The JWT signature algorithm we will be using to sign the token
			SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

			String secertkey = "432a462d4a614e645267556b586e3272357538782f413f4428472b4b6250655368566d5971337336763979244226452948404d635166546a576e5a7234753777";

			// We will sign our JWT with our ApiKey secret
			byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secertkey);
			Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

			long nowMillis = System.currentTimeMillis();
			Date now = new Date(nowMillis);
			long expMillis = nowMillis +  Long.parseLong("10800000"); // 3 hour 10800000 millesecond
			Date exp = new Date(expMillis);
			String jwtToken = Jwts.builder() // Build Token
					.setHeaderParams(headers)// Set headers
					.setClaims(claims)// Add claims
					.setExpiration(exp) // a java.util.Date
					.setIssuedAt(now) // add created time
					.signWith(signingKey) // Sign Token
					.compact(); //
			log.info("jwtToken: " + jwtToken);
			return jwtToken;
		} catch (Exception e) {
			errorsDAO.save(new BugsAndError(LocalDateTime.now(),"createJWT",e.getMessage()));
			throw new BusinessException("At  createJWT  Fun " + e.getMessage(), e);
		}

	}

	public Jws<Claims> decodeJWT(String jwt) throws BusinessException {
		try {
			log.info("Enter decodeJWT Function.with token =" + jwt);
			String secertkey = "432a462d4a614e645267556b586e3272357538782f413f4428472b4b6250655368566d5971337336763979244226452948404d635166546a576e5a7234753777";
            Jws<Claims> claim = Jwts.parserBuilder().setSigningKey(secertkey).build().parseClaimsJws(jwt);
			return claim;
		} catch (Exception e) {
			log.info(e.getMessage());
			errorsDAO.save(new BugsAndError(LocalDateTime.now(),"decodeJWT",e.getMessage()));
			throw new BusinessException(e.getMessage(), e);
		}

	}
	
}
