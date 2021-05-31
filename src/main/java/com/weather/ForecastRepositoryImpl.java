package com.weather;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.Optional;

public class ForecastRepositoryImpl implements ForecastRepository {
    private SessionFactory sessionFactory;
    private Session session;

    public ForecastRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Forecast> findForecastByLocationAndDate(Location location, LocalDate date) {
        session = sessionFactory.openSession();
        Forecast forecast = session.createQuery("SELECT f FROM Forecast f INNER JOIN Location l WHERE l.id = :locationId AND f.localDate = :localDate", Forecast.class)
                .setParameter("locationId", location.getId())
                .setParameter("localDate", date)
                .getSingleResult();
        session.close();
        return Optional.of(forecast);
    }

    @Override
    public Forecast saveForecast(Forecast forecast) {
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(forecast);
        transaction.commit();
        session.close();
        return forecast;
    }
}
