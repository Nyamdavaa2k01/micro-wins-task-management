package com.micro_wins.view.login;

import com.micro_wins.service.UserService;
import com.micro_wins.view.StageManager;
import com.micro_wins.view.main.MainPane;
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
        String userName = String.valueOf(loginPane.getUserName());
        Integer deviceId = Integer.valueOf(loginPane.getDeviceId());

        if (userService.authenticate(userName, deviceId)){
            stageManager.rebuildStage(MainPane.class);
        }
        else
            loginPane.getLblLogin().setText("Login Failed.");
    }
}
