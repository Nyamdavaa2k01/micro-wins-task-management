package com.micro_wins.model;

import jdk.jfr.DataAmount;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 06/05/2022 - 1:59 AM
 */

@Entity
@Table(name = "mw_task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", nullable = false)
    private int taskId ;

    @Column(name = "task_title", nullable = false)
    private String taskTitle;

    @Column(name = "task_definition")
    private String taskDefinition;

    @Column(name = "task_priority")
    private int taskPriority ;

    @Column(name = "task_status")
    private int taskStatus ;

    @Column(name = "task_category")
    private String taskCategory ;

    @Column(name = "task_start_date")
    private Date taskStartDate ;

    @Column(name = "task_deadline")
    private Date taskDeadline ;

    @Column(name = "task_user_id")
    private int taskUserId ;

    @Column(name = "task_pro_id")
    private int taskProId ;

    @Column(name = "task_pro_title")
    private String taskProTitle;

    public Task() {
    }

    public Task(String taskTitle, String taskDefinition, int taskPriority, int taskStatus, Date taskStartDate, int taskUserId) {
        this.taskTitle = taskTitle ;
        this.taskDefinition = taskDefinition ;
        this.taskPriority = taskPriority ;
        this.taskStatus = taskStatus ;
        this.taskStartDate = taskStartDate ;
        this.taskUserId = taskUserId ;
    }


    public Task(int taskId, String taskTitle, String taskDefinition, int taskPriority, int taskStatus, String taskCategory, Date taskStartDate, Date taskDeadline) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskDefinition = taskDefinition;
        this.taskPriority = taskPriority;
        this.taskStatus = taskStatus;
        this.taskCategory = taskCategory;
        this.taskStartDate = taskStartDate;
        this.taskDeadline = taskDeadline;
    }

    public Task(int taskId, String taskTitle, String taskDefinition, int taskPriority, int taskStatus, String taskCategory, Date taskStartDate, Date taskDeadline, int taskUserId, int taskProId, String taskProTitle) {
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

    public String getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(String taskCategory) {
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

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskTitle='" + taskTitle + '\'' +
                ", taskDefinition='" + taskDefinition + '\'' +
                ", taskPriority=" + taskPriority +
                ", taskStatus=" + taskStatus +
                ", taskCategory=" + taskCategory +
                ", taskStartDate=" + taskStartDate +
                ", taskDeadline=" + taskDeadline +
                ", taskUserId=" + taskUserId +
                ", taskProId=" + taskProId +
                ", taskProTitle='" + taskProTitle + '\'' +
                '}';
    }
}
