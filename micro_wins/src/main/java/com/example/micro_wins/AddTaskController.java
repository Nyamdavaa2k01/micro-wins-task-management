/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @definition Header дээрх "+" товчийг дарахад гарж ирэх ба хэрхэн цонх болгон үзүүлж буйг HeaderMenuController-оос харна уу.
 */

package com.example.micro_wins;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;

public class AddTaskController {

    Stage addTaskStage ;
    private int p ;

    @FXML
    public void initialize() {
//        addTaskStage = (Stage)  addTaskBtn.getScene().getWindow() ;
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
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("priorityButtons.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 200, 172);
        Stage priorityStage = new Stage() ;
        priorityStage.setScene(scene);
        priorityStage.initStyle(StageStyle.UNDECORATED);
        Bounds setPriorityBtnBounds = setPriorityBtn.localToScreen(setPriorityBtn.getBoundsInLocal()) ;
        int priorityButtonsInitPosX = (int) setPriorityBtnBounds.getMinX();
        int priorityButtonsInitPosY = (int) setPriorityBtnBounds.getMaxY() ;
        priorityStage.setX(priorityButtonsInitPosX);
        priorityStage.setY(priorityButtonsInitPosY);
        priorityStage.show();
        PriorityButtonsController p = new PriorityButtonsController() ;
        while(p.getPriority() == 4) {
            System.out.println(p.getPriority());
        }
        System.out.println(p.getPriority());
    }

    @FXML
    void setProject(ActionEvent event) {

    }

    @FXML
    void setTime(ActionEvent event) {

    }

}
