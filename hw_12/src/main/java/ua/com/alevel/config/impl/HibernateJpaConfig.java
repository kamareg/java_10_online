package ua.com.alevel.config.impl;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ua.com.alevel.config.JpaConfig;

public class HibernateJpaConfig implements JpaConfig {

    private final EntityManagerFactory entityManagerFactory;

    public HibernateJpaConfig() {
        this.entityManagerFactory = Persistence
                .createEntityManagerFactory("jpa-hibernate-mysql");
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
