package com.weather.location;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class LocationRepositoryImpl implements LocationRepository {

    private SessionFactory sessionFactory;
    private Session session;

    public LocationRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Location save(Location location) {
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.persist(location);

        transaction.commit();
        session.close();

        return location;
    }

    @Override
    public List<Location> getAllLocation() {
        session = sessionFactory.openSession();
        List<Location> resultList = session.createQuery("SELECT l FROM Location l", Location.class)
                .getResultList();
        session.close();

        return resultList;
    }

    @Override
    public Optional<Location> findById(Long id) {
        try {
            session = sessionFactory.openSession();
            Location location = session.createQuery("SELECT l FROM Location l WHERE l.id= :id", Location.class)
                    .setParameter("id", id)
                    .getSingleResult();
            session.close();
            return Optional.of(location);
        } catch (Exception e){
            return Optional.empty();
        }
    }
}
