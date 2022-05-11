package com.example.micro_wins;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 06/05/2022 - 1:59 AM
 */

public class Task {
    private int taskId ;
    private String taskTitle;
    private String taskDefinition;
    private int taskPriority ;
    private int taskStatus ;
    private int taskCategory ;
    private Date taskStartDate ;
    private Date taskDeadline ;
    private int taskUserId ;
    private int taskProId ;
    private String taskProTitle;

    public Task(int taskId, String taskTitle, String taskDefinition, int taskPriority, int taskStatus, int taskCategory, Date taskStartDate, Date taskDeadline) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskDefinition = taskDefinition;
        this.taskPriority = taskPriority;
        this.taskStatus = taskStatus;
        this.taskCategory = taskCategory;
        this.taskStartDate = taskStartDate;
        this.taskDeadline = taskDeadline;
    }

    public Task(int taskId, String taskTitle, String taskDefinition, int taskPriority, int taskStatus, int taskCategory, Date taskStartDate, Date taskDeadline, int taskUserId, int taskProId, String taskProTitle) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskDefinition = taskDefinition;
        this.taskPriority = taskPriority;
        this.taskStatus = taskStatus;
        this.taskCategory = taskCategory;
        this.taskStartDate = taskStartDate;
        this.taskDeadline = taskDeadline;
        this.taskUserId = taskUserId;
        this.taskProId = taskProId;
        this.taskProTitle = taskProTitle;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDefinition() {
        return taskDefinition;
    }

    public void setTaskDefinition(String taskDefinition) {
        this.taskDefinition = taskDefinition;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(int taskPriority) {
        this.taskPriority = taskPriority;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(int taskCategory) {
        this.taskCategory = taskCategory;
    }

    public Date getTaskStartDate() {
        return taskStartDate;
    }

    public void setTaskStartDate(Date taskStartDate) {
        this.taskStartDate = taskStartDate;
    }

    public Date getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(Date taskDeadline) {
        this.taskDeadline = taskDeadline;
    }

    public int getTaskUserId() {
        return taskUserId;
    }

    public void setTaskUserId(int taskUserId) {
        this.taskUserId = taskUserId;
    }

    public int getTaskProId() {
        return taskProId;
    }

    public void setTaskProId(int taskProId) {
        this.taskProId = taskProId;
    }

    public String getTaskProTitle() {
        return taskProTitle;
    }

    public void setTaskProTitle(String taskProTitle) {
        this.taskProTitle = taskProTitle;
    }
}
