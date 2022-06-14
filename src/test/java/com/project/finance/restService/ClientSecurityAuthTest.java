package com.project.finance.restService;



import com.project.finance.dbServices.ClientService;
import com.project.finance.entities.Client;
import com.project.finance.entities.Role;
import com.project.finance.jwt.provider.JwtProvider;
import com.project.finance.services.ClientDetailsImpl;
import com.project.finance.services.ClientDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientSecurityAuthTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JwtProvider jwtProvider;

    private MockMvc mvc;
    private Client client;
    @MockBean
    ClientDetailsService clientDetailsService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        client = new Client(1L,"login","email","password", LocalDateTime.now(), Set.of(new Role(1L,"USER")));
    }


    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mvc.perform(get("/protect").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldSaveNewClientSucceed() throws Exception {
        String token = jwtProvider.generateTokenForClientLogin("login");
        ClientDetailsImpl clientDetails = ClientDetailsImpl.build(client);
        when(clientDetailsService.loadUserByUsername(any())).thenReturn(clientDetails);
        Assertions.assertNotNull(token);
        mvc.perform(MockMvcRequestBuilders.get("/protect").header("Authorization", "Bearer " + token)).andExpect(status().isOk());
    }

}
