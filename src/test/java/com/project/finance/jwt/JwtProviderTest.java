package com.project.finance.jwt;

import com.project.finance.entities.Client;
import com.project.finance.entities.Role;
import com.project.finance.jwt.provider.JwtProvider;
import com.project.finance.services.ClientDetailsImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class JwtProviderTest {

    private ClientDetailsImpl clientDetails;
    @Autowired
    private JwtProvider jwtProvider;

    @BeforeEach
    public void init(){
        SimpleGrantedAuthority user = new SimpleGrantedAuthority("USER");
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(user);
        clientDetails = new ClientDetailsImpl(
                Long.valueOf(1),
                "login",
                "email",
                "password",
                grantedAuthorities);
    }

    @Test
    public void shouldGenerateJwtTokenWithClientDetails(){
        String jwtToken = jwtProvider.generateJwtToken(clientDetails);
        Assertions.assertNotNull(jwtToken);
    }

    @Test
    public void shouldGenerateJwtTokenWithClientLogin(){
        String jwtToken = jwtProvider.generateTokenForClientLogin("login");
        Assertions.assertNotNull(jwtToken);
    }
}
