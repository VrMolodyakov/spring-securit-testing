package com.project.finance.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String token;
    private final String refreshToken;
    private final String login;

}
