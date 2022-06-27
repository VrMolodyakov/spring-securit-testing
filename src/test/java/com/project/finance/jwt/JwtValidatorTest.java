package com.project.finance.jwt;

import com.project.finance.jwt.provider.JwtProvider;
import com.project.finance.jwt.validator.JwtTokenValidator;
import com.project.finance.services.client.ClientDetailsImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class JwtValidatorTest {

    private ClientDetailsImpl clientDetails;
    @Autowired
    private JwtTokenValidator jwtTokenValidator;
    @Autowired
    private JwtProvider jwtProvider;
    private String token;
    String expiredToken;

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
        Instant now = Instant.now(); //current date
        Instant before = now.minus(Duration.ofDays(1));
        Date expiredDate = Date.from(before);

    }

    @Test
    public void shouldValidateJwtToken(){
        boolean isValid = jwtTokenValidator.validateJwtToken(token, clientDetails);
        Assertions.assertEquals(true,isValid);
    }

    @Test
    public void shouldNotValidateExpiredJwtToken(){
        String fakeExpiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsb2dpbm4iLCJpYXQiOjE2NTUyNDQyMDUsImV4cCI6MTY1NTI0NDI2NX0.b0mkbtDb12RFYdiURY0LZYi2Fpy2JRJzCLPYBVWaP5cdO2vhiaypOwSdIeWr4lYsUYKrSktCAtWBeUL8V_I2kQ";
        boolean isValid = jwtTokenValidator.validateJwtToken(fakeExpiredToken, clientDetails);
        Assertions.assertEquals(false,isValid);
    }
}
