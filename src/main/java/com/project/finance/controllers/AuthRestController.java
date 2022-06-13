package com.project.finance.controllers;

import com.project.finance.controllers.dto.LoginRequest;
import com.project.finance.controllers.dto.RegistrationRequest;
import com.project.finance.controllers.dto.JwtResponse;
import com.project.finance.dbServices.ClientService;
import com.project.finance.dbServices.RefreshTokenDBService;
import com.project.finance.entities.Client;
import com.project.finance.entities.RefreshToken;
import com.project.finance.exception.ExpiredTokenException;
import com.project.finance.jwt.provider.JwtProvider;
import com.project.finance.services.ClientDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class AuthRestController {

    private final ClientService dbClientService;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenDBService refreshTokenDBService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateClient(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ClientDetailsImpl clientDetails = (ClientDetailsImpl) authentication.getPrincipal();
        String jwt = jwtProvider.generateJwtToken(clientDetails);
        RefreshToken refreshToken = refreshTokenDBService.createNewRefreshToken(clientDetails.getId());
        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(),clientDetails.getUsername()));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registerClient(@Valid @RequestBody RegistrationRequest registrationRequest){
        Client client = Client.builder()
                .email(registrationRequest.getEmail())
                .login(registrationRequest.getLogin())
                .password(registrationRequest.getPassword()).build();
        boolean successSave = dbClientService.save(client);
        if(successSave){
            return ResponseEntity.ok("Client was registered successfully!");
        }
        return ResponseEntity
                .badRequest()
                .body("email already exists");

    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(RequestEntity<String> refreshTokenRequest){
        String refreshToken = refreshTokenRequest.getBody();
        return refreshTokenDBService.findByToken(refreshToken)
                .map(refreshTokenDBService::deleteIfExpired)
                .map(RefreshToken::getClient)
                .map(client -> {
                    String token = jwtProvider.generateTokenForClientLogin(client.getLogin());
                    Map<String, Object> body = new LinkedHashMap<>();
                    body.put("accessToken", token);
                    body.put("refreshToken", refreshToken);
                    return new ResponseEntity<>(body, HttpStatus.OK);
                })
                .orElseThrow(() -> new ExpiredTokenException(refreshToken,
                        "Refresh token expired."));

    }
}

