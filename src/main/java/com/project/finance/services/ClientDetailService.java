package com.project.finance.services;

import com.project.finance.clientDBService.ClientService;
import com.project.finance.entities.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientDetailService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    ClientService clientService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("--------------------- INSIDE DETAIL SERVICE -----------------------");
        System.out.println(username);
        Client client = clientService.findClientByLogin(username).get();
        System.out.println(client);
        logger.debug(username + " try to log in");
        List<GrantedAuthority> authorities = client.getRoles().stream().
                map(authority -> new SimpleGrantedAuthority(authority.getRole())).
                collect(Collectors.toList());
        return buildUser(client,authorities);

    }

    private UserDetails buildUser(Client client,  List<GrantedAuthority> authorities){
        return new User(client.getLogin(),client.getPassword(),true,true,true,true,authorities);

    }
}
