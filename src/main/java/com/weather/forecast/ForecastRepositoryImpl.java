package com.weather.forecast;

import com.weather.location.Location;
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
        try {
            session = sessionFactory.openSession();
            Forecast forecast = session.createQuery("SELECT f FROM Forecast AS f WHERE f.location.id = :locationId AND f.localDate = :localDate", Forecast.class)
                    .setParameter("locationId", location.getId())
                    .setParameter("localDate", date)
                    .getSingleResult();
            session.close();
            return Optional.of(forecast);
        } catch (Exception e){
            return Optional.empty();
        }
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
