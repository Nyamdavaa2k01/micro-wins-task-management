package com.micro_wins.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 15/05/2022 - 5:59 PM
 */

@Entity
@Table(name = "mw_task_result")
public class Result {
    @Id
    @Column(name = "res_id", nullable = false)
    private int resId;

    @Column(name = "task_id", nullable = false)
    private int taskId ;
    private float taskCompletionPercent ;
    private Date taskCompletedDate ;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public double getTaskCompletionPercent() {
        return taskCompletionPercent;
    }

    public void setTaskCompletionPercent(float taskCompletionPercent) {
        this.taskCompletionPercent = taskCompletionPercent;
    }

    public Date getTaskCompletedDate() {
        return taskCompletedDate;
    }

    public void setTaskCompletedDate(Date taskCompletedDate) {
        this.taskCompletedDate = taskCompletedDate;
    }
}
