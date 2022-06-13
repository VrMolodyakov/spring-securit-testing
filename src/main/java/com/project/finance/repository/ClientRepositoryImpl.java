package com.project.finance.repository;

import com.project.finance.entities.Client;
import com.project.finance.entities.Role;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ClientRepositoryImpl implements ClientRepository {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Optional<Client> findByClientLogin(String login) {
        String hqlRequest = "from Client C JOIN FETCH C.roles where C.login =:login";
        Query query = sessionFactory.getCurrentSession().createQuery(hqlRequest).setParameter("login",login);
        List<Client> rs = (List<Client>) query.getResultList();
        if(rs.isEmpty()){
            return Optional.empty();
        }
        return Optional.of( (Client) query.getResultList().get(0) );
    }

    @Override
    public Optional<Client> findByClientEmail(String email) {
        String hqlRequest = "from Client C JOIN FETCH C.roles where C.email =:email";
        Query query = sessionFactory.getCurrentSession().createQuery(hqlRequest).setParameter("email",email);
        List<Client> rs = (List<Client>) query.getResultList();
        if(rs.isEmpty()){
            return Optional.empty();
        }
        return Optional.of( (Client) query.getResultList().get(0) );
    }

    @Override
    public Optional<Client> findByClientId(Long id) {
        String hqlRequest = "from Client C JOIN FETCH C.roles where C.id =:id";
        Query query = sessionFactory.getCurrentSession().createQuery(hqlRequest).setParameter("id",id);
        List<Client> rs = (List<Client>) query.getResultList();
        if(rs.isEmpty()){
            return Optional.empty();
        }
        return Optional.of( (Client) query.getResultList().get(0) );
    }

    @Override
    public boolean deleteUserById(Long id) {
        return false;
    }

    @Override
    public Optional<Client> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Client> findAllUsers() {
        return null;
    }

    @Override
    public boolean saveClient(Client client) {
        Optional<Client> byLogin = this.findByClientLogin(client.getLogin());
        Optional<Client> byEmail = this. findByClientEmail(client.getEmail());
        if(!byLogin.isPresent() && !byEmail.isPresent()){
            client.setRoles(Collections.singleton(new Role(1L, "USER")));
            client.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
            client.setCreationDate(LocalDateTime.now());
            System.out.println(client);
            sessionFactory.getCurrentSession().save(client);
            return true;
        }
        return false;
    }

    @Override
    public void deleteByUserName(String name) {

    }


}
