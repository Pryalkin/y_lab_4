package com.pryalkin.dao.impl;

import com.pryalkin.config.HibernateSessionFactoryUtil;
import com.pryalkin.dao.UserDao;
import com.pryalkin.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {

    @Override
    public User findById(Long id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id);
    }

    @Override
    public void save(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }

    @Override
    public List<User> findAll() {
        List<User> users = (List<User>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From User").list();
        return users;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        List<User> users = session.createQuery("FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .list();
        tx1.commit();
        session.close();
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        List<User> users = session.createQuery("FROM User u WHERE u.username = :username AND u.password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .list();
        tx1.commit();
        session.close();
        return Optional.ofNullable(users.get(0));
    }

    @Override
    public Optional<List<User>> findByRole(String role) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        List<User> users = session.createQuery("FROM User u WHERE u.role = :role", User.class)
                .setParameter("role", role)
                .list();
        tx1.commit();
        session.close();
        return Optional.ofNullable(users);
    }
}
