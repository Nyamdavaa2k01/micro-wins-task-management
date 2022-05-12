package com.micro_wins.model;

import javax.persistence.*;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 13/05/2022 - 1:21 AM
 */

@Entity
@Table(name="mw_project_task")
public class ProjectTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_task_id", updatable = false, nullable = false)
    private int proTaskId;
    private int proId;
    private int handlerId;
    private int proOwner;
    private int taskId;
    private int taskStatus;

    public int getProTaskId() {
        return proTaskId;
    }

    public void setProTaskId(int proTaskId) {
        this.proTaskId = proTaskId;
    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public int getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(int handlerId) {
        this.handlerId = handlerId;
    }

    public int getProOwner() {
        return proOwner;
    }

    public void setProOwner(int proOwner) {
        this.proOwner = proOwner;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return "ProjectTask{" +
                "proTaskId=" + proTaskId +
                ", proId=" + proId +
                ", handlerId=" + handlerId +
                ", proOwner=" + proOwner +
                ", taskId=" + taskId +
                ", taskStatus=" + taskStatus +
                '}';
    }
}
