package com.micro_wins.service.impl;

import com.micro_wins.model.Task;
import com.micro_wins.repository.TaskRepo;
import com.micro_wins.service.CrudService;
import com.micro_wins.service.TaskService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 13/05/2022 - 4:58 AM
 */
@Service
public class TaskServiceImpl implements CrudService<Task>, TaskService {
    EntityManager entityManager ;

    public TaskServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager ;
    }

    @Transactional
    public Task save(Task task) {
        entityManager.persist(task);
        return task ;
    }

    @Override
    public Task update(Task entity) {
        return null;
    }

    @Override
    public void delete(Task entity) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public void deleteInBatch(List<Task> entities) {

    }

    @Override
    public Task findById(Integer id) {
        return null;
    }

    @Override
    public List<Task> findAll() {
        return null;
    }

    @Override
    public List<Task> getTaskById(int id) {
        return null;
    }
}
