package com.pryalkin.dao.impl;

import com.pryalkin.config.HibernateSessionFactoryUtil;
import com.pryalkin.dao.CarDao;
import com.pryalkin.model.Car;
import com.pryalkin.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CarDaoImpl implements CarDao {

    @Override
    public Car findById(Long id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Car.class, id);
    }

    @Override
    public void save(Car car) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(car);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(Car car) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(car);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Car car) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(car);
        tx1.commit();
        session.close();
    }

    @Override
    public List<Car> findAll() {
        List<Car> cars = (List<Car>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Car").list();
        return cars;
    }

    @Override
    public Optional<List<Car>> findByBrandAndModel(String brand, String model) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        List<Car> cars = session.createQuery("FROM Car c WHERE c.brand = :brand AND c.model = :model", Car.class)
                .setParameter("brand", brand)
                .setParameter("model", model)
                .list();
        tx1.commit();
        session.close();
        return Optional.ofNullable(cars);
    }
}
