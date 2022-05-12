/**
 * @author Nyamka
 * @project micro-wins-task-management
 */

package com.micro_wins.view.main;

import com.micro_wins.MainApp;
import com.micro_wins.view.StageManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;

@Controller
@FxmlView
public class HeaderMenuPane {

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
    private TextField searchTextField;

    @FXML
    private Button seeNavBarBtn;

    @FXML
    private Button seeNotificationBtn;

    //Stage stage = (Stage) restoreDownWindowBtn.getScene().getWindow() ;


    @FXML
    void addTask(ActionEvent event) throws IOException {
        stageManager = springAppContext.getBean(StageManager.class);
        //stageManager.rebuildStage(AddTaskPane.class);
        if (taskStage != null) taskStage.close();
        taskStage = new Stage() ;
        URL fxmlLocation = getClass().getResource("AddTaskPane.fxml");
        System.out.println(fxmlLocation);
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation) ;
        taskStage.initStyle(StageStyle.UNDECORATED);
        taskStage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(fxmlLoader.load(), 624, 228, Color.TRANSPARENT) ;
        taskStage.setScene(scene);
        taskStage.getScene().getRoot().setEffect(new DropShadow());
        taskStage.getScene().setFill(Color.TRANSPARENT);
        taskStage.show();
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

}
