package com.pryalkin.dao.impl;

import com.pryalkin.config.HibernateSessionFactoryUtil;
import com.pryalkin.dao.LoggingUserDao;
import com.pryalkin.model.LoggingUser;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoggingUserDaoImpl implements LoggingUserDao {

    @Override
    public LoggingUser findById(Long id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(LoggingUser.class, id);
    }

    @Override
    public void save(LoggingUser loggingUser) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(loggingUser);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(LoggingUser loggingUser) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(loggingUser);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(LoggingUser loggingUser) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(loggingUser);
        tx1.commit();
        session.close();
    }

    @Override
    public List<LoggingUser> findAll() {
        List<LoggingUser> loggingUsers = (List<LoggingUser>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From LoggingUser").list();
        return loggingUsers;
    }
}
