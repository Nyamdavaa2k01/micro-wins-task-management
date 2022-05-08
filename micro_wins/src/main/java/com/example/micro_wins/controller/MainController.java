/**
 * @author Nyamka
 * @project micro-wins-task-management
 */

package com.example.micro_wins.controller;

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