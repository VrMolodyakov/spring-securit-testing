package com.project.finance.clientDBService;

import com.project.finance.entities.Client;
import com.project.finance.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    ClientRepository clientRepository;

    @Override
    public boolean save(Client client) {
        System.out.println("--------------------- HAS BEEN SAVED -----------------------");
        logger.info(client + " has been saved");
        return clientRepository.saveUser(client);
    }

    @Override

    public Optional<Client> findClientByLogin(String login) {
        System.out.println("--------------------- TRY TO FIND -----------------------");
        return clientRepository.findByClientLogin(login);
    }

    @Override
    public Client findClientByEmail(String email) {
        return null;
    }
}
