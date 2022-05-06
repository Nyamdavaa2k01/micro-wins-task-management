package com.example.micro_wins;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 06/05/2022 - 1:46 AM
 */

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.sql.*;

public class TodayViewController {

    Connection connection ;

    @FXML
    private ListView<Task> taskList;

    @FXML
    public void initialize () throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/micro_wins", "root", "pass") ;
        Statement getTaskStatement = connection.createStatement() ;
        ResultSet tasksInDatabase = getTaskStatement.executeQuery("SELECT * FROM mw_task") ;
        while (tasksInDatabase.next()) {
            int taskId = tasksInDatabase.getInt("task_id") ;
            String taskTitle = tasksInDatabase.getString("task_title") ;
            String taskDefinition = tasksInDatabase.getString("task_definition") ;
            int taskPriority = tasksInDatabase.getInt("task_priority") ;
            int taskStatus = tasksInDatabase.getInt("task_status");
            int taskCategory = tasksInDatabase.getInt("task_category") ;
            java.util.Date taskStartDate = tasksInDatabase.getDate("task_start_date") ;
            java.util.Date taskDeadline = tasksInDatabase.getDate("task_deadline") ;
            taskList.getItems().add(
                    new Task(taskId, taskTitle, taskDefinition, taskPriority, taskStatus, taskCategory, taskStartDate, taskDeadline)
            );
        }
        int i ;
        for (i = 0 ; i <taskList.getItems().size() ; i ++) {
            System.out.println(taskList.getItems().get(i).getTaskTitle());
        }
        taskList.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
            @Override
            public ListCell<Task> call (ListView<Task> taskList) {
                ListCell<Task> customTaskCell = new ListCell<Task>() {
                    @Override
                    public void updateItem(Task task, boolean empty) {
                        super.updateItem(task, empty);
                        if (empty) {
                            setGraphic(null);
                        }
                        else {
                            /**
                             * View of task in list
                             * There is task-on-today.fxml containing the view of task
                             * on today list this will be converted to java code here for
                             * easy use in future
                             */
                            setText(task.getTaskTitle());
                            Button tempBtn = new Button() ;
                            tempBtn.setPrefWidth(35);
                            tempBtn.setPrefHeight(35);
                            tempBtn.setStyle(
                                    "-fx-background-color:  #EAB6B6; " +
                                            "-fx-border-color :  red ; " +
                                            "-fx-border-width : 5 ;" +
                                            "-fx-border-radius : 50;" +
                                            "-fx-background-radius:50;"
                            );
                            setGraphic(tempBtn);


                        }
                    }
                };
                return customTaskCell;
            }
        });

    }

    @FXML
    private Button setFilterOptionBtn;

    @FXML
    private Button setSortOptionBtn;

    @FXML
    private VBox tasksListRoot;

    @FXML
    private Label todayDateLbl;

    @FXML
    void setFilterOption(ActionEvent event) {

    }

    @FXML
    void setSortOption(ActionEvent event) {

    }

}
