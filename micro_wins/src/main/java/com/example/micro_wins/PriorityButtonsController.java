package com.example.micro_wins;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 02/05/2022 - 8:11 PM
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class PriorityButtonsController {

    @FXML
    private Button priority1Btn;

    @FXML
    private Button priority2Btn;

    @FXML
    private Button priority3Btn;

    @FXML
    private Button priority4Btn;

    @FXML
    private VBox priorityButtonBar;

    @FXML
    void selectPriority(ActionEvent event) {
        if (event.getSource() == priority1Btn) {
            System.out.println("p1");
        }
        else if (event.getSource() == priority2Btn) {
            System.out.println("p2");
        }
        else if (event.getSource() == priority3Btn) {
            System.out.println("p3");
        }
        else {
            System.out.println("p4");
        }
    }
}