package com.project.finance.dbServices;

import com.project.finance.entities.Client;
import com.project.finance.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final static Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    ClientRepository clientRepository;

    @Override
    @Transactional
    public boolean save(Client client) {
        logger.info(client + " has been saved");
        return clientRepository.saveClient(client);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findClientByLogin(String login) {
        logger.info("try to find with login {}",login);
        return clientRepository.findByClientLogin(login);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findClientByEmail(String email) {
        logger.info("try to find with email {}",email);
        return clientRepository.findByClientEmail(email);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    public Optional<Client> findClientById(Long id) {
        logger.info("try to find with id {}",id);
        return clientRepository.findByClientId(id);
    }

    @Override
    public int deleteClientById(Long id) {
        logger.info("try to delete with id {}",id);
        return clientRepository.deleteClientById(id);
    }
}
