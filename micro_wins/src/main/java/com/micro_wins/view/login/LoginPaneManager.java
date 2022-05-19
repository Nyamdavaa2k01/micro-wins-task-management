package com.micro_wins.view.login;

import com.micro_wins.service.UserService;
import com.micro_wins.view.StageManager;
import com.micro_wins.holder.UserHolder;
import com.micro_wins.model.User;

import com.micro_wins.view.main.TodayPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 11/05/2022 - 10:40 AM
 */
@Component
public class LoginPaneManager
{
    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private StageManager stageManager;

    public void login(LoginPane loginPane)
    {
        String userName = String.valueOf(loginPane.getUserNameTxt());
        Integer deviceId = Integer.valueOf(loginPane.getUserDeviceId());

        if (userService.authenticate(userName, deviceId)){

            /**
             * when user login, hold user data
             */
            UserHolder userHolder = UserHolder.getInstance();
            User user = userService.findByUserNameAndDeviceId(userName, deviceId);
            userHolder.setUser(user);

            /**
             * jump to Today Page
             */
            stageManager.rebuildStage(TodayPane.class);
        } else {
            loginPane.getLblLogin().setText("Login Failed.");
        }
    }
}
