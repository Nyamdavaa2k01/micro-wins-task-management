package com.micro_wins.service.impl;

import com.micro_wins.model.Task;
import com.micro_wins.model.User;
import com.micro_wins.repository.TaskRepo;
import com.micro_wins.repository.UserRepo;
import com.micro_wins.service.CrudService;
import com.micro_wins.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private TaskRepo taskRepo;

    @Override
    public Task save(Task entity)
    {
        return taskRepo.save(entity);
    }

    @Override
    public Task update(Task entity)
    {
        return taskRepo.save(entity);
    }

    @Override
    public void delete(Task entity)
    {
        taskRepo.delete(entity);
    }

    @Override
    public void deleteById(Integer id)
    {
        taskRepo.deleteById(id);
    }

    @Override
    public void deleteInBatch(List<Task> entities) {

    }

    @Override
    public Task findById(Integer id)
    {
        return taskRepo.findById(id).orElse(null);
    }

    @Override
    public List<Task> findAll() {
        return (List<Task>) taskRepo.findAll();
    }

}