package com.example.micro_wins.service;

import java.util.List;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 11/05/2022 - 10:20 AM
 */
public interface CrudService<T extends Object> {
    T save(T entity);

    T update(T entity);

    void delete(T entity);

    void deleteById(Integer id);

    void deleteInBatch(List<T> entities);

    T findById(Integer id);

    List<T> findAll();
}
