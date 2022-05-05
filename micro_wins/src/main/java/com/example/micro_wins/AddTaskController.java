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
import javafx.scene.control.DatePicker;
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
import java.time.LocalDate;
import java.util.ArrayList;

public class AddTaskController {

    Stage addTaskStage ;
    private Stage priorityButtonsStage ;
    Connection connection ;
    /**
     * priority 4 is choosen by default
     */
    private int priority = 4;
    private final String BUTTON_STYLE = "-fx-background-color:transparent; -fx-border-color:gray; -fx-cursor:hand" ;
//    private final String HOVERED_BUTTON_STYLE = "-fx-background-color:-fx-shadow-highlight-color; -fx-border-color:gray; -fx-cursor:hand";


    @FXML
    public void initialize() throws SQLException {
        taskDatePicker.setValue(LocalDate.now());
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/micro_wins", "root", "pass") ;

    }

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
    private DatePicker taskDatePicker;

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
        if (priorityButtonsStage != null) priorityButtonsStage.close();
        priorityButtonsStage = new Stage( );
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
        priorityButtonsStage.setScene(priorityButtonsScene);
        priorityButtonsStage.show();
    }
    /**
     * The following method (setProject) had been used to set project of the task when
     * user click set priority button.
     *
     * User cannot add tasks to project from private space so the following code will not be used.
     * However, the code is only commented but not deleted for possible future use in project tasks!
     */
//
//    @FXML
//    private Button setProjectBtn;

//    @FXML
//    void setProject(ActionEvent event) throws SQLException {
//        ArrayList<Button> projectButtons = new ArrayList<>();
//        Statement statement = connection.createStatement() ;
//        ResultSet projectsInDatabase = statement.executeQuery("SELECT * FROM mw_project") ;
//        while (projectsInDatabase.next()) {
//            String projectName = projectsInDatabase.getString("pro_title") ;
//            Button projectBtn = new Button(projectName) ;
//            projectBtn.setPrefHeight(43);
//            projectBtn.setPrefWidth(200);
//            projectBtn.setStyle(BUTTON_STYLE);
//            projectBtn.setAlignment(Pos.TOP_LEFT);
//            ImageView buttonGraphicImage = new ImageView(new Image("file:micro_wins/src/main/resources/images/inbox-icon.png")) ;
//            buttonGraphicImage.setFitHeight(30);
//            buttonGraphicImage.setFitWidth(30);
//            projectBtn.setGraphic(buttonGraphicImage);
//            projectButtons.add(projectBtn) ;
//
//
//        }
//
//        VBox projectButtonsRoot = new VBox() ;
//        Scene projectButtonsScene = new Scene(projectButtonsRoot, 200, projectButtons.size()*43) ;
//
//        int i ;
//        for (i = 0 ; i < projectButtons.size() ; i ++) {
//            projectButtonsRoot.getChildren().add(projectButtons.get(i)) ;
//        }
//
//        /**
//         * Stage is created down here because scene creation need number of active projects in database
//         */
//        Stage projectButtonsStage = new Stage() ;
//        projectButtonsStage.setScene(projectButtonsScene);
//        projectButtonsStage.initStyle(StageStyle.UNDECORATED);
//     //   projectButtonsStage.initStyle(StageStyle.TRANSPARENT);
//        projectButtonsStage.show();
//    }



}