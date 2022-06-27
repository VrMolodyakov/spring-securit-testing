package com.project.finance.jwt.provider;

import com.project.finance.services.client.ClientDetailsImpl;

public interface JwtProvider {
    String generateJwtToken(ClientDetailsImpl clientDetails);
    String generateTokenForClientLogin(String login);
}
