package com.example.micro_wins;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
// somehow cant use javafx.awt.*

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX + Spring Boot Application!");
    }
}