package com.project.finance.clientDBService;

import com.project.finance.entities.Client;

import java.util.Optional;

public interface ClientService {
    boolean save(Client client);
    Optional<Client> findClientByLogin(String login);
    Client findClientByEmail(String email);
}
