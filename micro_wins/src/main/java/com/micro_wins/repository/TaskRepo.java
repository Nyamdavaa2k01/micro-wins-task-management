package com.micro_wins.repository;

import com.micro_wins.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 13/05/2022 - 4:40 AM
 */

@Repository
public interface TaskRepo extends JpaRepository<Task, Integer> {
    List<Task> findByTaskStatus (int taskStatus) ;
    List<Task> findByOrderByTaskPriorityAsc() ;
    List<Task> findByTaskPriority(int taskPriority) ;
    List<Task> findByTaskPriorityAndTaskStatus (int taskPriority, int taskStatus) ;
    List<Task> findByTaskStartDate (Date date) ;
}
