package com.micro_wins.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Bagaa
 * @project micro_wins
 * @created 08/05/2022 - 7:44 PM
 */

@Entity
@Table(name="mw_project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_id", updatable = false, nullable = false)
    private int proId;
    private String proTitle;
    private int proOwner;
    private float proCompletionPercent;
    private String proDescription;
    private int proStatus;
    private Date proStartDate;
    private Date proDeadline;

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public String getProTitle() {
        return proTitle;
    }

    public void setProTitle(String proTitle) {
        this.proTitle = proTitle;
    }

    public int getProOwner() {
        return proOwner;
    }

    public void setProOwner(int proOwner) {
        this.proOwner = proOwner;
    }

    public float getProCompletionPercent() {
        return proCompletionPercent;
    }

    public void setProCompletionPercent(float proCompletionPercent) {
        this.proCompletionPercent = proCompletionPercent;
    }

    public String getProDescription() {
        return proDescription;
    }

    public void setProDescription(String proDescription) {
        this.proDescription = proDescription;
    }

    public int getProStatus() {
        return proStatus;
    }

    public void setProStatus(int proStatus) {
        this.proStatus = proStatus;
    }

    public Date getProStartDate() {
        return proStartDate;
    }

    public void setProStartDate(Date proStartDate) {
        this.proStartDate = proStartDate;
    }

    public Date getProDeadline() {
        return proDeadline;
    }

    public void setProDeadline(Date proDeadline) {
        this.proDeadline = proDeadline;
    }

    @Override
    public String toString() {
        return "Project{" +
                "proId=" + proId +
                ", proTitle='" + proTitle + '\'' +
                ", proOwner=" + proOwner +
                ", proCompletionPercent=" + proCompletionPercent +
                ", proDescription='" + proDescription + '\'' +
                ", proStatus=" + proStatus +
                ", proStartDate=" + proStartDate +
                ", proDeadline=" + proDeadline +
                '}';
    }
}
