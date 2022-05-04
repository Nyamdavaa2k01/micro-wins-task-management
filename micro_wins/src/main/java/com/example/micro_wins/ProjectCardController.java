package com.example.micro_wins;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ProjectCardController {
    public ProjectCardController()
            throws IOException {

        // attach FXML to this control instance
        FXMLLoader loader = new FXMLLoader(getClass().getResource("project-card-view.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    @FXML
    public void initialize() {

    }
}
