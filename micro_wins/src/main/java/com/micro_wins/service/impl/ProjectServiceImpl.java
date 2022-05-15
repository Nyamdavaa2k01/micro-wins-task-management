package com.micro_wins.service.impl;

import com.micro_wins.model.Project;
import com.micro_wins.repository.ProjectRepo;
import com.micro_wins.service.CrudService;
import com.micro_wins.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 13/05/2022 - 1:09 AM
 */

@Service
public class ProjectServiceImpl implements CrudService<Project>, ProjectService {
    @Autowired
    private ProjectRepo projectRepo;

    @Override
    public Project save(Project entity)
    {
        return projectRepo.save(entity);
    }

    @Override
    public Project update(Project entity)
    {
        return projectRepo.save(entity);
    }

    @Override
    public void delete(Project entity)
    {
        projectRepo.delete(entity);
    }

    @Override
    public void deleteById(Integer id)
    {
        projectRepo.deleteById(id);
    }

    @Override
    public Project findById(Integer id)
    {
        return projectRepo.findById(id).orElse(null);
    }

    @Override
    public List<Project> findAll()
    {
        return projectRepo.findAll();
    }

    @Override
    public boolean isCompleted(int proId, String proTitle)
    {
        return false;
    }

    @Override
    public List<Project> findByProTitleAndProOwner(String proTitle, int proOwner)
    {
        return projectRepo.findByProTitleAndProOwner(proTitle, proOwner);
    }

    @Override
    public void deleteInBatch(List<Project> projects)
    {
        projectRepo.deleteInBatch(projects);
    }

    @Override
    public List<Project> findProjectsByProOwner(int proOwner){
        List<Project> projectList = projectRepo.findAll();
        List<Project> proListWithOneOwner = new ArrayList<>();
        projectList.forEach((project -> {
            if(project.getProOwner() == proOwner){
                proListWithOneOwner.add(project);
            }
        }));
        return proListWithOneOwner;
    }

}
