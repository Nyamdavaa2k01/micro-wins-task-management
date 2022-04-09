package com.example.micro_wins.model.repository;

import com.example.micro_wins.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Integer> {
}
