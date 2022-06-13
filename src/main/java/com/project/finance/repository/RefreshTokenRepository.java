package com.project.finance.repository;

import com.project.finance.entities.Client;
import com.project.finance.entities.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {
    Optional<RefreshToken> findById(Long id);
    Optional<RefreshToken> findByToken(String token);
    RefreshToken saveToken(RefreshToken refreshToken);
    RefreshToken delete(RefreshToken refreshToken);
    int deleteByClient(Client client);
}
