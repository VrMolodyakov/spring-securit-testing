package com.project.finance.jwt.parser;

public interface JwtParser {
    String getLoginFromToken(String token);
}
