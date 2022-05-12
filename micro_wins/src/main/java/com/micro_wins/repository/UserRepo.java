package com.micro_wins.repository;

import com.micro_wins.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 11/05/2022 - 10:40 AM
 */

@Repository
public interface UserRepo extends JpaRepository<User, Integer>
{

    User findByUserName(String userName);
}