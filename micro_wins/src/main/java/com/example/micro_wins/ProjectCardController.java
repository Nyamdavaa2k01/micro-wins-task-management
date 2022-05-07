package com.example.micro_wins;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ProjectCardController {

    @FXML
    private ResourceBundle resources;

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

    public ProjectCardController()
            throws IOException {
        // attach FXML to this control instance
        FXMLLoader loader = new FXMLLoader(getClass().getResource("project-card-view.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }
}
