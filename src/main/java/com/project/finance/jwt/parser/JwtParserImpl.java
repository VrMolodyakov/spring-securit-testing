package com.project.finance.jwt.parser;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtParserImpl implements JwtParser {
    @Value("${finance.app.jwtSecret}")
    private String jwtSecret;
    @Override
    public String getLoginFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
