package com.micro_wins.repository;

import com.micro_wins.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 13/05/2022 - 4:40 AM
 */
@Repository
public interface TaskRepo extends CrudRepository<Task, Integer> {

}
