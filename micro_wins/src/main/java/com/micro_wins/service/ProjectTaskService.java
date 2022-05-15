package com.micro_wins.service;

import com.micro_wins.model.Project;
import com.micro_wins.model.ProjectTask;

import java.util.List;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 13/05/2022 - 2:10 AM
 */
public interface ProjectTaskService {
    List<ProjectTask> getProjectTaskByProjectId(int proId) ;
    List<ProjectTask> getProjectTaskByHandlerId(int handlerId) ;
    List<ProjectTask> getProjectTaskByProOwner(int proOwner) ;
    ProjectTask findByProTaskId(int taskId);
}
