package com.micro_wins.view.holder;

import com.micro_wins.model.Project;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 16/05/2022 - 2:52 AM
 */

public final class ProjectHolder {
    private Project project;
    private final static ProjectHolder INSTANCE = new ProjectHolder();

    private ProjectHolder() {}

    public static ProjectHolder getInstance() {
        return INSTANCE;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return this.project;
    }

}
