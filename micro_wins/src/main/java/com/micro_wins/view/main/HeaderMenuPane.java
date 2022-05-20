/**
 * @author Nyamka
 * @project micro-wins-task-management
 */

package com.micro_wins.view.main;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @definition HeaderMenu is used in UpcomingPane, ProjectPane, InboxPane, TodayPane controllers.
 * HeaderMenu also acts as custom title bar with buttons (restore down, minimize/maximize stage, close stage) which is
 * found in title bar of usual applications.
 */


import com.micro_wins.view.FxController;
import com.micro_wins.view.StageManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
@FxmlView
public class HeaderMenuPane implements Initializable, FxController {

    private StageManager stageManager;

    @Autowired
    ConfigurableApplicationContext springAppContext;

    boolean isScreenMaximized = true ;
    double screenPosX = 0;
    double screenPosY = 0;
    double screenWidth = 1000;
    double screenHeight = 600;

    private Stage taskStage ;

    @FXML
    private Button addTaskBtn;

    @FXML
    private Button closeWindowBtn;

    @FXML
    private Button collapseWindowBtn;

    @FXML
    private AnchorPane header;

    @FXML
    private HBox manageWindowSection;

    @FXML
    private Button navToHomeBtn;

    @FXML
    private Button restoreDownWindowBtn;

    @FXML
    private ImageView search;

    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchTxt;

    @FXML
    private Button seeNavBarBtn;

    @FXML
    private Button seeNotificationBtn;

    @FXML
    void addTask(ActionEvent event) {
        stageManager = springAppContext.getBean(StageManager.class);
        stageManager.addStage(AddTaskPane.class);
    }

    @FXML
    void closeWindow(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void collapseWindow(ActionEvent event) {
        Stage stage = (Stage) restoreDownWindowBtn.getScene().getWindow() ;
        stage.setIconified(true);
    }

    @FXML
    void navToHome(ActionEvent event) {

    }

    @FXML
    void restoreDownWindow(ActionEvent event) {
        Stage stage = (Stage) restoreDownWindowBtn.getScene().getWindow() ;
        if (isScreenMaximized) {
            stage.setX(screenPosX);
            stage.setY(screenPosY);
            stage.setWidth(screenWidth);
            stage.setHeight(screenHeight);
            isScreenMaximized = false ;
        }
        else {
            screenPosX = stage.getX() ;
            screenPosY = stage.getY() ;
            screenHeight = stage.getHeight() ;
            screenWidth = stage.getWidth() ;
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds() ;
            stage.setX(primaryScreenBounds.getMinX());
            stage.setY(primaryScreenBounds.getMinY());
            stage.setWidth(primaryScreenBounds.getWidth());
            stage.setHeight(primaryScreenBounds.getHeight());
            isScreenMaximized = true ;
        }
    }

    @FXML
    void search(ActionEvent event) {

    }

    @FXML
    void seeNavBar(ActionEvent event) {

    }

    @FXML
    void seeNotification(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stageManager = springAppContext.getBean(StageManager.class);
    }
}
