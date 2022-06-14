package com.project.finance.jwt.validator;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenValidatorImpl implements JwtTokenValidator {

    private final static Logger logger = LoggerFactory.getLogger(JwtTokenValidatorImpl.class);
    @Value("${finance.app.jwtSecret}")
    private String jwtSecret;

    @Override
    public boolean validateJwtToken(String token, UserDetails details) {
        final String username = getUsernameFromToken(token);
        System.out.println(username);
        return (username.equals(details.getUsername()) && !isExpired(token));
    }

    private Boolean isExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private  <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

    }

    private String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
}
