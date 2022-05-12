package com.micro_wins.view.main;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

import java.net.URL;

@Controller
@FxmlView
public class ProjectCardController {

    @FXML
    private URL location;

    @FXML
    private ImageView is_owner;

    @FXML
    private GridPane pro_task;

    @FXML
    private Label pro_task_name;

    @FXML
    private Circle project_status;

    @FXML
    void initialize(){
        assert is_owner != null : "fx:id=\"is_owner\" was not injected: check your FXML file 'project-card-view.fxml'.";
        assert pro_task != null : "fx:id=\"pro_task\" was not injected: check your FXML file 'project-card-view.fxml'.";
        assert pro_task_name != null : "fx:id=\"pro_task_name\" was not injected: check your FXML file 'project-card-view.fxml'.";
        assert project_status != null : "fx:id=\"project_status\" was not injected: check your FXML file 'project-card-view.fxml'.";
    }

}
