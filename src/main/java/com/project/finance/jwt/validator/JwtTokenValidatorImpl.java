package com.project.finance.jwt.validator;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenValidatorImpl implements JwtTokenValidator {

    private final static Logger logger = LoggerFactory.getLogger(JwtTokenValidatorImpl.class);
    @Value("${finance.app.jwtSecret}")
    private String jwtSecret;

    @Override
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException |
                MalformedJwtException |
                UnsupportedJwtException |
                IllegalArgumentException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        }
        return false;
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
