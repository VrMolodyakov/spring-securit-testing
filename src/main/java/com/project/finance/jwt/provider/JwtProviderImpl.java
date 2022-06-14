package com.project.finance.jwt.provider;

import com.project.finance.services.ClientDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtProviderImpl implements JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProviderImpl.class);
    @Value("${finance.app.jwtSecret}")
    private String jwtSecret;
    @Value("${finance.app.jwtExpirationSec}")
    private int jwtExpirationSeconds;

    @Override
    public String generateJwtToken(ClientDetailsImpl clientDetails) {
        return generateTokenForClientLogin(clientDetails.getUsername());

    }

    @Override
    public String generateTokenForClientLogin(String login){
        Date expireTime = Date.from(LocalDateTime.now().plusSeconds(jwtExpirationSeconds).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(new Date())
                .setExpiration(expireTime)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
