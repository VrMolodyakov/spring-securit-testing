package com.project.finance.repository;

import com.project.finance.entities.Client;
import com.project.finance.entities.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.sql.Ref;
import java.util.List;
import java.util.Optional;

@Repository
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Optional<RefreshToken> findById(Long id) {
        String hqlRequest = "from RefreshToken RT JOIN FETCH RT.client where RT.id =:id";
        Query query = sessionFactory.getCurrentSession().createQuery(hqlRequest).setParameter("id",id);
        List<Client> rs = (List<Client>) query.getResultList();
        if(rs.isEmpty()){
            return Optional.empty();
        }
        return Optional.of( (RefreshToken) query.getResultList().get(0) );
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        String hqlRequest = "from RefreshToken RT JOIN FETCH RT.client where RT.token =:token";
        Query query = sessionFactory.getCurrentSession().createQuery(hqlRequest).setParameter("token",token);
        List<Client> rs = (List<Client>) query.getResultList();
        if(rs.isEmpty()){
            return Optional.empty();
        }
        return Optional.of( (RefreshToken) query.getResultList().get(0) );
    }

    @Override
    public RefreshToken saveToken(RefreshToken refreshToken) {
        sessionFactory.getCurrentSession().save(refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshToken delete(RefreshToken refreshToken) {
        sessionFactory.getCurrentSession().delete(refreshToken);
        return refreshToken;
    }

    @Override
    public int deleteByClient(Client client) {
        String hqlRequest = "delete from RefreshToken RT where RT.client =:client";
        Query query = sessionFactory.getCurrentSession().createQuery(hqlRequest);
        query.setParameter("client", client);
        return query.executeUpdate();
    }
}
