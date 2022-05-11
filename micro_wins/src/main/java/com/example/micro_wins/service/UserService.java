package com.example.micro_wins.service;

import com.example.micro_wins.model.User;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 11/05/2022 - 10:29 AM
 */

public interface UserService {
    boolean authenticate(String userName, Integer deviceId);

    User findByUserName(String userName);
}

