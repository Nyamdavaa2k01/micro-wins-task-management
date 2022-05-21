package com.micro_wins.view.login;


import com.micro_wins.holder.InitUserHolder;
import com.micro_wins.holder.UserHolder;
import com.micro_wins.model.Project;
import com.micro_wins.model.User;
import com.micro_wins.repository.ProjectRepo;
import com.micro_wins.repository.UserRepo;
import com.micro_wins.view.FxController;
import com.micro_wins.view.StageManager;
import com.micro_wins.view.main.TodayPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
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

    @Autowired
    private ProjectRepo projectRepo;

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

    @Autowired
    @Lazy
    private StageManager stageManager;

    @Autowired
    ConfigurableApplicationContext springAppContext;

    private static final Logger LOG =LoggerFactory.getLogger(LoginPane.class);
    private final String DEFAULT_PRO_TITLE = "inbox";
    private final float DEFAULT_COMPLETE_PERCENT = 0;
    private final int DEFAULT_STATUS = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        User initUser = InitUserHolder.getInstance().getUser();
        LOG.debug(initUser.toString());
        User exitsUser = userRepo.findByUserNameAndDeviceId(initUser.getUserName(), initUser.getDeviceId());

        EventHandler<ActionEvent> loginEventHandler = event -> loginManager.login(this);

        deviceId.setOnAction(loginEventHandler);
        btnLogin.setOnAction(loginEventHandler);

        if(exitsUser == null) {
            User savedUser = userRepo.save(initUser);

            Project newProject = new Project();

            if (savedUser != null) {

                newProject.setProTitle(DEFAULT_PRO_TITLE);
                newProject.setProOwner(savedUser.getUserId());
                newProject.setProCompletionPercent(DEFAULT_COMPLETE_PERCENT);
                newProject.setProDescription("");
                newProject.setProStatus(DEFAULT_STATUS);
                newProject.setProStartDate(null);
                newProject.setProDeadline(null);

                Project savedProject = projectRepo.save(newProject);

                if(savedProject != null){
                    initUserNameLb.setText("Your username: " + savedUser.getUserName());
                    initUserNameLb.setStyle("-fx-border-color: #f00; -fx-border-radius: 5px;");
                    initUserDeviceIdLb.setText("Your device ID: " + savedUser.getDeviceId());
                    initUserDeviceIdLb.setStyle("-fx-border-color: #f00; -fx-border-radius: 5px;");
                }
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
