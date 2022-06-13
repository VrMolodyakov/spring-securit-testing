package com.project.finance.jwt.validator;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenValidator {
    boolean validateJwtToken(String token, UserDetails details);
}
