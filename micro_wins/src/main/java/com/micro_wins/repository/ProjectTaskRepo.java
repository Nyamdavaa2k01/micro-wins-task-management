package com.micro_wins.repository;

import com.micro_wins.model.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 13/05/2022 - 1:38 AM
 */
@Repository
public interface ProjectTaskRepo extends JpaRepository<ProjectTask, Integer> {
        ProjectTask findByProTaskId(int taskId);
}