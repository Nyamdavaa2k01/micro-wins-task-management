package com.micro_wins.repository;

import com.micro_wins.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 13/05/2022 - 1:04 AM
 */

@Repository
public interface ProjectRepo extends JpaRepository<Project, Integer> {
    Project findByProTitle(String proTitle);
    List<Project> findProjectsByProOwner(int proOwner);
}
