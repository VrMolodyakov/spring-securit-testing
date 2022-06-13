package com.project.finance.jwt.validator;

public interface JwtTokenValidator {
    boolean validateJwtToken(String token);
}
