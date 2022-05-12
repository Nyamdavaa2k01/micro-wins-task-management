package com.micro_wins.model;
//import lombok.Data;
//import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


/**
 * @author Bagaa
 * @project micro_wins
 * @created 08/05/2022 - 7:44 PM
 */

@Entity
@Table(name="mw_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
    private int userId;
    private int deviceId;
    private String userName;
    private int userStatus;

    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", deviceId=" + deviceId +
                ", userName='" + userName + '\'' +
                ", userStatus=" + userStatus +
                '}';
    }
}
