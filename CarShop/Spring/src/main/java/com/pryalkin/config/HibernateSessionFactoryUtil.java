package com.pryalkin.config;

import com.pryalkin.model.Car;
import com.pryalkin.model.LoggingUser;
import com.pryalkin.model.Order;
import com.pryalkin.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

public class HibernateSessionFactoryUtil {

    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try  {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Car.class);
                configuration.addAnnotatedClass(Order.class);
                configuration.addAnnotatedClass(LoggingUser.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                System.err.println("Исключение при создании SessionFactory: " + e);
            }
        }
        return sessionFactory;
    }
}


