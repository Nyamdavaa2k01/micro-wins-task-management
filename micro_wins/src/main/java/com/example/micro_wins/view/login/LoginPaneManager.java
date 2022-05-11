package com.example.micro_wins.view.login;

import com.example.micro_wins.controller.MainController;
import com.example.micro_wins.service.UserService;
import com.example.micro_wins.view.StageManager;
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
            stageManager.rebuildStage(MainController.class);
        }
        else
            loginPane.getLblLogin().setText("Login Failed.");
    }
}
