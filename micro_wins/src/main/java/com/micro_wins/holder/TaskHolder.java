package com.micro_wins.holder;

import com.micro_wins.model.Task;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 18/05/2022 - 10:52 AM
 */

public final class TaskHolder {
    private Task task;
    private final static TaskHolder INSTANCE = new TaskHolder();

    private TaskHolder() {}

    public static TaskHolder getInstance() {
        return INSTANCE;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return this.task;
    }

}
