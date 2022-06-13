package com.project.finance.clientAuthenticationFilter;


import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ClientLoginRequest {
    String login;
    String password;
}
