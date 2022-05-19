package com.micro_wins.repository;

import com.micro_wins.model.Result;
import com.micro_wins.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 15/05/2022 - 5:57 PM
 */

@Repository
public interface ResultRepo extends JpaRepository<Result, Integer> {
    List<Result> findByTaskCompletedDate (Date date) ;
}
