package ua.com.alevel.config.impl;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.com.alevel.config.HibernateService;

public class HibernateConfig implements HibernateService {

    private final SessionFactory sessionFactory;

    public HibernateConfig() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
