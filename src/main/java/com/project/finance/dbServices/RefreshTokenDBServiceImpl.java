package com.project.finance.dbServices;

import com.project.finance.entities.Client;
import com.project.finance.entities.RefreshToken;
import com.project.finance.exception.ClientNotFoundException;
import com.project.finance.exception.ExpiredTokenException;
import com.project.finance.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class RefreshTokenDBServiceImpl implements RefreshTokenDBService {

    private final static Logger LOGGER = LoggerFactory.getLogger(RefreshTokenDBServiceImpl.class);
    private final RefreshTokenRepository tokenRepository;
    private final ClientService clientService;

    @Value("${finance.app.jwtRefreshExpirationSec}")
    private int jwtRefreshExpirationSec;

    @Override
    @Transactional(readOnly = true)
    public Optional<RefreshToken> findById(Long id) {
        LOGGER.info("try to find token with id : {}",id);
        return tokenRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RefreshToken> findByToken(String token) {
        LOGGER.info("try to find token with token : {}",token);
        return tokenRepository.findByToken(token);
    }

    @Override
    @Transactional
    public int deleteByClientId(Long id) {
        LOGGER.info("try to delete token by id : {}",id);
        Client client = clientService
                .findClientById(id)
                .orElseThrow(() -> new ClientNotFoundException(String.format("id : %s cannot be found", id)));
        return tokenRepository.deleteByClient(client);

    }

    @Override
    @Transactional
    public RefreshToken deleteIfExpired(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            tokenRepository.delete(token);
            throw new ExpiredTokenException(token.getToken(), "Refresh token was expired.");
        }
        return token;
    }

    @Override
    @Transactional
    public RefreshToken createNewRefreshToken(Long clientId) {
        Client client = clientService
                .findClientById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(String.format("id : %s cannot be found", clientId)));
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setClient(client);
        refreshToken.setExpiryDate(Instant.now().plusSeconds(jwtRefreshExpirationSec));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken = tokenRepository.saveToken(refreshToken);
        return refreshToken;
    }
}
