package com.micro_wins.service;

import com.micro_wins.model.Task;

import java.util.Date;
import java.util.List;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 13/05/2022 - 4:56 AM
 */
public interface TaskService {
    List<Task> findByTaskStatus (int taskStatus) ;
    List<Task> findByOrderByTaskPriorityAsc() ;
    List<Task> findByTaskPriority(int taskPriority) ;
    List<Task> findByTaskPriorityAndTaskStatus (int taskPriority, int taskStatus) ;
    List<Task> findByTaskStartDate (Date date) ;
    List<Task> findByTaskStartDateAndTaskStatus(Date date, int taskStatus) ;

    /**
     * @author Bagaa
     * @param taskUserId
     * @param taskProId
     * @return
     */
    List<Task> findByTaskUserIdAndTaskProId(int taskUserId, int taskProId);
}
