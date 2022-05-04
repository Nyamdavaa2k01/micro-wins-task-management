/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @definition Header дээрх "+" товчийг дарахад гарж ирэх ба хэрхэн цонх болгон үзүүлж буйг HeaderMenuController-оос харна уу.
 */

package com.example.micro_wins;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;

public class AddTaskController {

    Stage addTaskStage ;
    /**
     * priority 4 is choosen by default
     */
    private int priority = 4;
    private final String BUTTON_STYLE = "-fx-background-color:transparent; -fx-border-color:gray; -fx-cursor:hand" ;
//    private final String HOVERED_BUTTON_STYLE = "-fx-background-color:-fx-shadow-highlight-color; -fx-border-color:gray; -fx-cursor:hand";



    @FXML
    private Button addReminderBtn;

    @FXML
    private Button addTaskBtn;

    @FXML
    private AnchorPane addTaskView;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button setPriorityBtn;

    @FXML
    private Button setProjectBtn;

    @FXML
    private Button setTimeBtn;

    @FXML
    private TextField taskDescriptionTxt;

    @FXML
    private TextField taskNameTxt;

    @FXML
    void addReminder(ActionEvent event) {

    }

    @FXML
    void addTask(ActionEvent event) throws SQLException {
        String taskTitle = taskNameTxt.getText() ;
        String taskDefinition = taskDescriptionTxt.getText();
//        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/micro_wins", "root", "pass") ;
//        Statement statement = connection.createStatement() ;
//        ResultSet resultSet = statement.executeQuery("SELECT * FROM mw_task") ;
//        while (resultSet.next()) {
//            System.out.println(resultSet.getString("task_title"));
//        }
        //statement.executeUpdate("INSERT INTO mw_task(task_title, task_definition) VALUES ( ' " + taskTitle + " ',' " + taskDefinition + " ');") ;
        addTaskStage = (Stage)  addTaskBtn.getScene().getWindow() ;
        addTaskStage.close();
     }

    @FXML
    void cancel(ActionEvent event) {
        addTaskStage = (Stage)  addTaskBtn.getScene().getWindow() ;
        addTaskStage.close();
    }


    @FXML
    void setPriority(ActionEvent event) throws IOException {
        VBox priorityButtonsRoot = new VBox() ;
        Scene priorityButtonsScene = new Scene(priorityButtonsRoot, 200, 172) ; 
        Stage priorityButtonsStage = new Stage( );
        priorityButtonsStage.initStyle(StageStyle.UNDECORATED);

        /**
         * Changing the initial position of priorityButtonsStage relative to setPriorityBtn 
         */
        Bounds setPriorityBtnBounds = setPriorityBtn.localToScreen(setPriorityBtn.getBoundsInLocal()) ;
        int priorityButtonsStageInitPosX = (int) setPriorityBtnBounds.getMinX();
        int priorityButtonsStageInitPosY = (int) setPriorityBtnBounds.getMaxY() ;
        priorityButtonsStage.setX(priorityButtonsStageInitPosX);
        priorityButtonsStage.setY(priorityButtonsStageInitPosY);

        
        Button [] priorityButtons = new Button[4] ;
        int i ;
        for (i = 0 ; i < 4 ; i ++) {
            priorityButtons[i] = new Button() ;
            priorityButtons[i].setAlignment(Pos.TOP_LEFT);
            priorityButtons[i].setStyle(BUTTON_STYLE);
            priorityButtons[i].setOnMouseClicked(e -> {
                String text = ((Button) e.getSource()).getText();
                priority = Integer.parseInt(text.replaceAll("[^0-9]", "")) ;
                ((Stage)((Button)e.getSource()).getScene().getWindow()).close();

            });
            String buttonText = "Priority " + (i+1) ;
            String buttonGraphicFilePath ="file:micro_wins/src/main/resources/images/priority-"+(i+1)+"-icon.png" ;
            ImageView buttonGraphicImage = new ImageView(new Image(buttonGraphicFilePath)) ;
            buttonGraphicImage.setFitHeight(30);
            buttonGraphicImage.setFitWidth(30);
            priorityButtons[i].setText(buttonText);
            priorityButtons[i].setGraphic(buttonGraphicImage);
            priorityButtons[i].setPrefWidth(200);
            priorityButtons[i].setPrefHeight(43);
            priorityButtonsRoot.getChildren().add(priorityButtons[i]) ;
        }


       // priorityButtonsRoot.getChildren().addAll(priority1Btn, priority2Btn, priority3Btn, priority4Btn) ;
        priorityButtonsStage.setScene(priorityButtonsScene);
        priorityButtonsStage.show();
    }


    @FXML
    void setProject(ActionEvent event) {

    }

    @FXML
    void setTime(ActionEvent event) {

    }

}
