package com.project.finance.jwt;

import com.project.finance.jwt.parser.JwtParser;
import com.project.finance.jwt.provider.JwtProvider;
import com.project.finance.services.ClientDetailsImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class JwtParserTest {

    private ClientDetailsImpl clientDetails;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private JwtParser jwtParser;
    private String token;

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
        token = jwtProvider.generateJwtToken(clientDetails);
    }

    @Test
    public void shouldSuccessfulParseJwtToken(){
        String login = jwtParser.getLoginFromToken(token);
        Assertions.assertEquals(login,"login");
    }
}
