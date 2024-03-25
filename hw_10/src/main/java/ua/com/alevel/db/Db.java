package ua.com.alevel.db;

import ua.com.alevel.entity.BaseEntity;

import java.util.ArrayList;
import java.util.Optional;

public interface Db<T extends BaseEntity> {

    void create(T entity);

    void update(T entity);

    void delete(String id);

    Optional<T> findById(String id);

    ArrayList<T> findAll();
}
