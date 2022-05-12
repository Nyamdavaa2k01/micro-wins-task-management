package com.micro_wins.service;

import com.micro_wins.model.Task;

import java.util.List;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 13/05/2022 - 4:56 AM
 */
public interface TaskService {
    List<Task> getTaskById (int id) ;
}
