package com.micro_wins.holder;

import com.micro_wins.model.User;

/**
 * @author Littl
 * @project micro-wins-task-management
 * @created 20/05/2022 - 4:10 AM
 * @purpose анхны хэрэглэгчийг хадгалах зорилготой
 * @definition анх программыг ажиллуулахад шинэ хэрэглэгч үүсгэх мэдээллийг хадгална.
 */
public class InitUserHolder {
    private User user;

    private final static InitUserHolder INSTANCE = new InitUserHolder();

    private InitUserHolder() {}

    public static InitUserHolder getInstance() {
        return INSTANCE;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
