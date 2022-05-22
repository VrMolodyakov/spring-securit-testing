package com.project.finance.repository;

import com.project.finance.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface ClientRepository  {
    Optional<Client> findByClientLogin(String name);
    Client findByClientEmail(String email);
    boolean deleteUserById(Long id);
    Optional<Client> findById(Long id);
    List<Client> findAllUsers();
    boolean saveUser(Client user);
    void deleteByUserName(String name);
}
