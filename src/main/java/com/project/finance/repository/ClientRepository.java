package com.project.finance.repository;

import com.project.finance.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface ClientRepository  {
    Optional<Client> findByClientLogin(String name);
    Optional<Client> findByClientEmail(String email);
    Optional<Client> findByClientId(Long id);
    boolean deleteUserById(Long id);
    Optional<Client> findById(Long id);
    List<Client> findAllUsers();
    boolean saveClient(Client user);
    void deleteByUserName(String name);
}
