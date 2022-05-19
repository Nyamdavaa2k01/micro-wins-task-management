package com.micro_wins.view.login;


import com.micro_wins.holder.InitUserHolder;
import com.micro_wins.model.User;
import com.micro_wins.repository.UserRepo;
import com.micro_wins.view.FxController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 11/05/2022 - 10:40 AM
 */

@Controller
@FxmlView
public class LoginPane implements Initializable, FxController
{
    @Autowired
    private LoginPaneManager loginManager;

    @Autowired
    private UserRepo userRepo;

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField deviceId;

    @FXML
    private TextField userName;

    @FXML
    private Label lblLogin;

    @FXML
    private Label initUserDeviceIdLb;

    @FXML
    private Label initUserNameLb;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        User initUser = InitUserHolder.getInstance().getUser();
        System.out.println(initUser.toString());
        User exitsUser = userRepo.findByUserNameAndDeviceId(initUser.getUserName(), initUser.getDeviceId());

        EventHandler<ActionEvent> loginEventHandler = event -> loginManager.login(this);

        deviceId.setOnAction(loginEventHandler);
        btnLogin.setOnAction(loginEventHandler);

        if(exitsUser == null) {
            User savedUser = userRepo.save(initUser);
            if (savedUser != null) {
                initUserNameLb.setText("Your username: " + savedUser.getUserName());
                initUserNameLb.setStyle("-fx-border-color: #f00; -fx-border-radius: 5px;");
                initUserDeviceIdLb.setText("Your device ID: " + savedUser.getDeviceId());
                initUserDeviceIdLb.setStyle("-fx-border-color: #f00; -fx-border-radius: 5px;");
            }
        }
    }

    public Integer getUserDeviceId()
    {
        return Integer.parseInt(deviceId.getText());
    }

    public String getUserNameTxt()
    {
        return userName.getText();
    }

    public Label getLblLogin()
    {
        return lblLogin;
    }
}
