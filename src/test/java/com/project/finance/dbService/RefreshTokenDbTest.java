package com.project.finance.dbService;

import com.project.finance.dbServices.ClientServiceImpl;
import com.project.finance.dbServices.RefreshTokenDBService;
import com.project.finance.entities.Client;
import com.project.finance.entities.RefreshToken;
import com.project.finance.entities.Role;
import com.project.finance.exception.ClientNotFoundException;
import com.project.finance.exception.ExpiredTokenException;
import com.project.finance.repository.RefreshTokenRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RefreshTokenDbTest {

    @Autowired
    private RefreshTokenDBService tokenDBService;

    @MockBean
    private ClientServiceImpl clientService;

    @MockBean
    private RefreshTokenRepository tokenRepository;

    private Client client;
    private RefreshToken refreshToken;

    @BeforeEach
    public void init(){
        client = new Client(1L,"login","email","password", LocalDateTime.now(), Set.of(new Role(1L,"USER")));
        refreshToken = new RefreshToken(1L,client,"refreshToken", Instant.now());
    }

    @Test
    public void shouldFindTokenById(){
        Long tokenId = refreshToken.getTokenId();
        when(tokenRepository.findById(tokenId)).thenReturn(Optional.of(refreshToken));
        Optional<RefreshToken> returnedToken = tokenDBService.findById(tokenId);
        Assertions.assertEquals(refreshToken,returnedToken.get(), "Token should be found");
    }

    @Test
    public void shouldFindTokenByTokenName(){
        String tokenName = refreshToken.getToken();
        when(tokenRepository.findByToken(tokenName)).thenReturn(Optional.of(refreshToken));
        Optional<RefreshToken> returnedToken = tokenDBService.findByToken(tokenName);

    }

    @Test
    public void shouldDeleteExpiredToken(){
        when(tokenRepository.delete(refreshToken)).thenReturn(refreshToken);
        Assertions.assertThrows(ExpiredTokenException.class,() -> tokenDBService.deleteIfExpired(refreshToken),"should throw");
    }

    @Test
    public void shouldSuccessDeleteByClientId(){
        Long clientId = 1L;
        when(clientService.findClientById(clientId)).thenReturn(Optional.of(client));
        when(tokenRepository.deleteByClient(client)).thenReturn(1);
        int givenRows = tokenDBService.deleteByClientId(clientId);
        Assertions.assertEquals(givenRows,1);
    }

    @Test
    public void shouldUnsuccessfulDeleteByClientId(){
        Long clientId = -1L;
        when(clientService.findClientById(clientId)).thenReturn(Optional.empty());
        when(tokenRepository.deleteByClient(client)).thenReturn(1);
        Assertions.assertThrows(ClientNotFoundException.class,() -> tokenDBService.deleteByClientId(clientId),"should throw");
    }

    @Test
    public void shouldSuccessfulCreateNewToken(){
        Long clientId = 1L;
        when(clientService.findClientById(clientId)).thenReturn(Optional.of(client));
        when(tokenRepository.saveToken(any())).thenReturn(refreshToken);
        RefreshToken givenToken = tokenDBService.createNewRefreshToken(clientId);
        Assertions.assertEquals(refreshToken,givenToken);
    }

    @Test
    public void shouldUnsuccessfulCreateNewToken(){
        Long clientId = -1L;
        when(clientService.findClientById(clientId)).thenReturn(Optional.empty());
        when(tokenRepository.saveToken(any())).thenReturn(refreshToken);
        Assertions.assertThrows(ClientNotFoundException.class,() -> tokenDBService.createNewRefreshToken(clientId),"should throw");
    }
}
