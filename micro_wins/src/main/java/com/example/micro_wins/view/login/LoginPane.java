package com.example.micro_wins.view.login;


import com.example.micro_wins.view.FxController;
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

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField deviceId;

    @FXML
    private TextField userName;

    @FXML
    private Label lblLogin;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        EventHandler<ActionEvent> loginEventHandler = event -> loginManager.login(this);

        deviceId.setOnAction(loginEventHandler);
        btnLogin.setOnAction(loginEventHandler);
    }

    public Integer getDeviceId()
    {
        return Integer.parseInt(deviceId.getText());
    }

    public String getUserName()
    {
        return userName.getText();
    }

    public Label getLblLogin()
    {
        return lblLogin;
    }
}
