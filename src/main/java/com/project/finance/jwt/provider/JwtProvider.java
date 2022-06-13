package com.project.finance.jwt.provider;

import com.project.finance.services.ClientDetailsImpl;
import org.springframework.security.core.Authentication;

public interface JwtProvider {
    String generateJwtToken(ClientDetailsImpl clientDetails);
    String generateTokenForClientLogin(String login);
}
