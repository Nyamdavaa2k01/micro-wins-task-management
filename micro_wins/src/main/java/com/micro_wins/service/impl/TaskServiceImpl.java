package com.micro_wins.service.impl;

import com.micro_wins.constants.ConstantDictionaryValues;
import com.micro_wins.model.Task;
import com.micro_wins.model.User;
import com.micro_wins.repository.TaskRepo;
import com.micro_wins.repository.UserRepo;
import com.micro_wins.service.CrudService;
import com.micro_wins.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
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

    ConstantDictionaryValues constantDictionaryValues ;

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

    @Override
    public List<Task> findByTaskStatus(int taskStatus) {
        constantDictionaryValues = new ConstantDictionaryValues() ;
        List<Task> activeTasks = new ArrayList<>() ;
        List<Task> allTasks = taskRepo.findAll() ;

        allTasks.forEach(task -> {
            if (task.getTaskStatus() == taskStatus) {
                activeTasks.add(task) ;
            }
        });

        return activeTasks ;
    }

    @Override
    public List<Task> findByOrderByTaskPriorityAsc() {
        List<Task> sortedTasks = taskRepo.findAll(Sort.by(Sort.Direction.ASC, "taskPriority")) ;
        return sortedTasks ;
    }

    @Override
    public List<Task> findByTaskPriority(int taskPriority)  {
        List<Task> allTasks = taskRepo.findAll() ;
        List<Task> filteredTasks = new ArrayList<>( );

        allTasks.forEach(task -> {
            if (task.getTaskPriority() == taskPriority) {
                filteredTasks.add(task) ;
            }
        });

        return filteredTasks ;
    }

    @Override
    public List<Task> findByTaskPriorityAndTaskStatus (int taskPriority, int taskStatus) {
        return taskRepo.findByTaskPriorityAndTaskStatus(taskPriority, taskStatus);
    }

    @Override
    public List<Task> findByTaskStartDate (Date date)  {
        return taskRepo.findByTaskStartDate(date) ;
    }
}