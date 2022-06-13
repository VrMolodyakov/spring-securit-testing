package com.project.finance.dbServices;

import com.project.finance.entities.Client;
import com.project.finance.entities.RefreshToken;

import java.util.Optional;

public interface RefreshTokenDBService {
    Optional<RefreshToken> findById(Long id);
    Optional<RefreshToken> findByToken(String token);
    int deleteByClientId(Long id);
    RefreshToken deleteIfExpired(RefreshToken token);
    RefreshToken createNewRefreshToken(Long clientId);
}
