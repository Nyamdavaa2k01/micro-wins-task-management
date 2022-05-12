package com.micro_wins.service;

import com.micro_wins.model.Project;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 13/05/2022 - 1:08 AM
 */
public interface ProjectService {
    boolean isCompleted(int proId, String proTitle);
    Project findByProTitle(String proTitle);
}
