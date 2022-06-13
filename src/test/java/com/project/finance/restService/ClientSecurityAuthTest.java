package com.project.finance.restService;



import com.project.finance.dbServices.ClientService;
import com.project.finance.entities.Client;
import com.project.finance.entities.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientSecurityAuthTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    Client client;
    @MockBean
    ClientService clientService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        client = new Client(1L,"login","email","password", LocalDateTime.now(), Set.of(new Role(1L,"USER")));
    }

    @WithMockUser
    @Test
    public void givenAuthRequestOnPrivateServiceShouldSucceedWith200() throws Exception {
        mvc.perform(get("/home").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSaveNewClientSucceed() throws Exception {
        Mockito.when(clientService.save(Mockito.any(Client.class))).thenReturn(true);
        Mockito.when(clientService.findClientByLogin(Mockito.any(String.class))).thenReturn(Optional.empty());
    }

}
