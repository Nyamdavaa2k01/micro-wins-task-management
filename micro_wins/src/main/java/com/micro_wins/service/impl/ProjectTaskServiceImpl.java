package com.micro_wins.service.impl;

import com.micro_wins.model.ProjectTask;
import com.micro_wins.repository.ProjectTaskRepo;
import com.micro_wins.service.CrudService;
import com.micro_wins.service.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 13/05/2022 - 2:14 AM
 */
@Service
public class ProjectTaskServiceImpl implements CrudService<ProjectTask>, ProjectTaskService {
    @Autowired
    private ProjectTaskRepo projectTaskRepo;

    @Override
    public ProjectTask save(ProjectTask entity)
    {
        return projectTaskRepo.save(entity);
    }

    @Override
    public ProjectTask update(ProjectTask entity)
    {
        return projectTaskRepo.save(entity);
    }

    @Override
    public void delete(ProjectTask entity)
    {
        projectTaskRepo.delete(entity);
    }

    @Override
    public void deleteById(Integer id)
    {
        projectTaskRepo.deleteById(id);
    }

    @Override
    public ProjectTask findById(Integer id)
    {
        return projectTaskRepo.findById(id).orElse(null);
    }

    @Override
    public List<ProjectTask> findAll()
    {
        return projectTaskRepo.findAll();
    }

    @Override
    public ProjectTask findByProTaskId(int taskId)
    {
        return projectTaskRepo.findByProTaskId(taskId);
    }

    @Override
    public List<ProjectTask> getProjectTaskByProjectId(int proId){
        List<ProjectTask> resList = new ArrayList<>();
        List<ProjectTask> allProjectTasks = findAll();

        allProjectTasks.forEach(projectTask -> {
            if(projectTask.getProId() == proId){
                resList.add(projectTask);
            }
        });

        return resList;
    }

    @Override
    public List<ProjectTask> getProjectTaskByHandlerId(int handlerId){
        List<ProjectTask> resList = new ArrayList<>();
        List<ProjectTask> allProjectTasks = findAll();

        allProjectTasks.forEach(projectTask -> {
            if(projectTask.getHandlerId() == handlerId){
                resList.add(projectTask);
            }
        });

        return resList;
    }

    @Override
    public List<ProjectTask> getProjectTaskByProOwner(int proOwner){
        List<ProjectTask> resList = new ArrayList<>();
        List<ProjectTask> allProjectTasks = findAll();

        allProjectTasks.forEach(projectTask -> {
            if(projectTask.getProOwner() == proOwner){
                resList.add(projectTask);
            }
        });

        return resList;
    }

    @Override
    public void deleteInBatch(List<ProjectTask> projectTasks)
    {
        projectTaskRepo.deleteInBatch(projectTasks);
    }
}
