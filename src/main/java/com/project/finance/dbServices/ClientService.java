package com.project.finance.dbServices;

import com.project.finance.entities.Client;

import java.util.Optional;

public interface ClientService {
    boolean save(Client client);
    Optional<Client> findClientByLogin(String login);
    Optional<Client> findClientByEmail(String email);
    Optional<Client> findClientById(Long id);
    int deleteClientById(Long id);
}
