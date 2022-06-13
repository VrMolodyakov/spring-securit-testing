package com.project.finance.dbService;


import com.project.finance.dbServices.ClientServiceImpl;
import com.project.finance.entities.Client;
import com.project.finance.entities.Role;
import com.project.finance.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClientDBServiceTests {

    @Autowired
    private ClientServiceImpl service;

    @MockBean
    private ClientRepository repository;

    private final String LOGIN = "login";

    @Test
    @DisplayName("Test findById Success")
    public void shouldSaveUserProperly(){
        when(repository.findByClientLogin(LOGIN)).thenReturn(Optional.empty());
        Optional<Client> returnedClient = service.findClientByLogin(LOGIN);
        Assertions.assertFalse(returnedClient.isPresent(), "Widget should not be found");
    }

    @Test
    @DisplayName("Test save")
    void shouldSaveClient() {
        // Setup our mock repository
        Client client = new Client(1L,"login","email","password", LocalDateTime.now(), Set.of(new Role(1L,"USER")));
        when(repository.saveClient(client)).thenReturn(true);
        boolean isSaved = service.save(client);
        Assertions.assertEquals(isSaved, true, "should save client");
    }
}
