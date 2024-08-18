package ua.com.alevel.service;

import ua.com.alevel.dto.DataTableRequest;
import ua.com.alevel.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public interface CrudService<E extends BaseEntity> {

    void create(E entity);

    void update(E entity);

    void delete(Long id);

    E findById(Long id);

    List<E> findAll();

    List<E> findSortAndLimit(DataTableRequest request);
}
