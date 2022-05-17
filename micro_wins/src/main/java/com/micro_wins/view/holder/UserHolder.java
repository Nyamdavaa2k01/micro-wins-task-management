package com.micro_wins.view.holder;

import com.micro_wins.model.User;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 17/05/2022 - 8:52 AM
 */

public final class UserHolder {
    private User user;

    private final static UserHolder INSTANCE = new UserHolder();

    private UserHolder() {}

    public static UserHolder getInstance() {
        return INSTANCE;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
