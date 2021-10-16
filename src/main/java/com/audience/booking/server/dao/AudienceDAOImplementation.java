package com.audience.booking.server.dao;

import com.audience.booking.server.entity.Audience;
import com.audience.booking.server.entity.Client;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class AudienceDAOImplementation implements AudienceDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Audience> getAllAudiences() {
        Session session = entityManager.unwrap(Session.class);
        List<Audience> allAudiences = session.createQuery("from Audience", Audience.class).getResultList();
        return allAudiences;
    }

    @Override
    @Transactional
    public void saveAudience(Audience audience) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(audience);
    }

    @Override
    @Transactional
    public Audience getAudience(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Audience.class, id);
    }

    @Override
    @Transactional
    public void deleteAudience(int id) {
        Session session = entityManager.unwrap(Session.class);
        Query employeeQuery = session.createQuery("delete from Audience " +
                "where id =:audienceId");
        employeeQuery.setParameter("audienceId", id);
        employeeQuery.executeUpdate();
    }
}
