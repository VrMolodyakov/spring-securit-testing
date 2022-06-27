package com.project.finance.services.client;

import com.project.finance.dbServices.ClientService;
import com.project.finance.entities.Client;
import com.project.finance.services.client.ClientDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    ClientService clientService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientService.findClientByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return ClientDetailsImpl.build(client);
    }
}
