package com.micro_wins.view.main;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 06/05/2022 - 1:46 AM
 */

import com.micro_wins.constants.ConstantValues;
import com.micro_wins.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.util.Callback;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

import java.sql.*;

@Controller
@FxmlView
public class TodayPane {

    Connection connection ;
    ConstantValues constantValues ;

    @FXML
    private ListView<Task> taskList;

    @FXML
    public void initialize () throws SQLException {
        constantValues = new ConstantValues() ;
        final double TASK_LIST_WIDTH = Screen.getPrimary().getVisualBounds().getWidth()*0.8 - 200 ;
        //final double TASK_CELL_HEIGHT = 1000 ;
        taskList.setPrefWidth(TASK_LIST_WIDTH);
        taskList.setStyle("-fx-border-color:white;");
        taskList.setFocusTraversable(false);
        taskList.setPadding(new Insets(20, 200, 0, 50));
       // taskList.getStylesheets().add("file:micro_wins/src/main/resources/styles/listview.css") ;
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/micro_wins", "root", "root") ;
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
                             * Individual Task View on Today List
                             */

                            AnchorPane taskOnTodayRoot = new AnchorPane() ;
                            taskOnTodayRoot.setPrefWidth(TASK_LIST_WIDTH-200);
                            taskOnTodayRoot.setPrefHeight(80);
                            Button finishTaskBtn = new Button() ;
                            AnchorPane.setLeftAnchor(finishTaskBtn, 50.0);
                            AnchorPane.setTopAnchor(finishTaskBtn, 14.0);
                            finishTaskBtn.setPrefHeight(35);
                            finishTaskBtn.setPrefWidth(35);
                            String finishTaskBtnBorderColor ;
                            if (task.getTaskPriority() == 1) finishTaskBtnBorderColor = constantValues.getPRIORITY1_COLOR() ;
                            else if (task.getTaskPriority() == 2) finishTaskBtnBorderColor = constantValues.getPRIORITY2_COLOR() ;
                            else if (task.getTaskPriority() == 3) finishTaskBtnBorderColor = constantValues.getPRIORITY3_COLOR() ;
                            else finishTaskBtnBorderColor = constantValues.getPRIORITY4_COLOR() ;
                            String finishTaskBtnBgColor = finishTaskBtnBorderColor + "40";
                            finishTaskBtn.setStyle(
                                    "-fx-background-color: " + finishTaskBtnBgColor +  "; " +
                                            "-fx-border-color :" +  finishTaskBtnBorderColor + "; " +
                                            "-fx-border-width : 5 ;" +
                                            "-fx-border-radius : 50;" +
                                            "-fx-background-radius:50;"
                            );

                            Label taskTitleLbl = new Label(task.getTaskTitle()) ;
                            AnchorPane.setLeftAnchor(taskTitleLbl, 113.0);
                            AnchorPane.setTopAnchor(taskTitleLbl, 14.0);
                            AnchorPane.setRightAnchor(taskTitleLbl, 50.0);
                            taskTitleLbl.setFont(Font.font("Times New Roman Bold", 18));

                            Label taskDescriptionLbl = new Label(task.getTaskDefinition()) ;
                            AnchorPane.setLeftAnchor(taskDescriptionLbl, 113.0);
                            AnchorPane.setBottomAnchor(taskDescriptionLbl, 15.0);
                            AnchorPane.setRightAnchor(taskDescriptionLbl, 50.0);
                            taskDescriptionLbl.setTextFill(Paint.valueOf("#4d4a4a"));
                            taskDescriptionLbl.setFont(Font.font("Times New Roman", 12));

                            Separator bottomSep = new Separator() ;
                            AnchorPane.setLeftAnchor(bottomSep, 50.0);
                            AnchorPane.setBottomAnchor(bottomSep, 5.0);
                            AnchorPane.setRightAnchor(bottomSep, 50.0);
                            bottomSep.setStyle("-fx-background-color:black;");

                            HBox editTaskButtons = new HBox() ;
                            AnchorPane.setRightAnchor(editTaskButtons, 51.0);
                            AnchorPane.setTopAnchor(editTaskButtons, 10.0);
                            editTaskButtons.setPrefWidth(0.088*TASK_LIST_WIDTH);
                            editTaskButtons.prefHeight(28) ;
                            editTaskButtons.setSpacing(0.015*TASK_LIST_WIDTH);

                            Button editTaskBtn = new Button() ;
                            ImageView editImage = new ImageView(new Image("file:micro_wins/src/main/resources/images/pen-icon.png")) ;
                            editImage.setFitWidth(20);
                            editImage.setFitHeight(20);
                            editTaskBtn.setGraphic(editImage);

                            Button seeMoreBtn = new Button() ;
                            ImageView seeMoreImage = new ImageView(new Image("file:micro_wins/src/main/resources/images/3dots-icon.png")) ;
                            seeMoreImage.setFitWidth(20);
                            seeMoreImage.setFitHeight(20);
                            seeMoreBtn.setGraphic(seeMoreImage);

                            Button changeProjectBtn = new Button() ;
                            if (task.getTaskProTitle() != null) changeProjectBtn.setText(task.getTaskProTitle());
                            else changeProjectBtn.setText("Inbox");
                            AnchorPane.setRightAnchor(changeProjectBtn, 51.0 );
                            AnchorPane.setBottomAnchor(changeProjectBtn, 11.0);
                            changeProjectBtn.setPrefWidth(0.088*TASK_LIST_WIDTH);
                            changeProjectBtn.setPrefHeight(28) ;
                            changeProjectBtn.setStyle("-fx-border-color:gray;" +
                                    "-fx-border-radius:10; ");

                            editTaskButtons.getChildren().addAll(editTaskBtn, seeMoreBtn) ;
                            taskOnTodayRoot.getChildren().addAll(finishTaskBtn, taskTitleLbl, taskDescriptionLbl, bottomSep, editTaskButtons, changeProjectBtn) ;
                            setGraphic(taskOnTodayRoot);

                            /**
                             * when user clicks button with an icon of pen, textfield shows and user can change textfield
                             */
                            editTaskBtn.setOnMouseClicked(e -> {
                                AnchorPane editTaskRootContainer = new AnchorPane() ;
                                AnchorPane editTaskRoot = new AnchorPane() ;
                                editTaskRootContainer.getChildren().add(editTaskRoot);
                                AnchorPane.setLeftAnchor(editTaskRoot, 50.0);
                                AnchorPane.setRightAnchor(editTaskRoot, 50.0);
                                editTaskRoot.setPrefHeight(204);
                                editTaskRoot.setMaxWidth(TASK_LIST_WIDTH-150);
                                editTaskRoot.getStylesheets().add("file:micro_wins/src/main/resources/styles/addTask.css") ;
                                editTaskRoot.setStyle("-fx-border-color:gray; " +
                                        "-fx-border-radius: 25;");
                                TextField taskNameTxt = new TextField() ;
                                taskNameTxt.setText(task.getTaskTitle());
                                AnchorPane.setLeftAnchor(taskNameTxt, 50.0);
                                AnchorPane.setTopAnchor(taskNameTxt, 10.0);
                                AnchorPane.setRightAnchor(taskNameTxt, 50.0);
                                taskNameTxt.setPrefHeight(35);

                                taskNameTxt.setFont(Font.font("Times New Roman Bold", 18));

                                TextField taskDescriptionTxt = new TextField() ;
                                taskDescriptionTxt.setText(task.getTaskDefinition());
                                taskDescriptionTxt.setPromptText("Description");
                                AnchorPane.setLeftAnchor(taskDescriptionTxt, 50.0);
                                AnchorPane.setTopAnchor(taskDescriptionTxt, 50.0);
                                AnchorPane.setRightAnchor(taskDescriptionTxt, 50.0);
                                taskDescriptionTxt.setAlignment(Pos.TOP_LEFT);
                                taskDescriptionTxt.setPrefHeight(50);

                                AnchorPane.setBottomAnchor(bottomSep, 10.0);

                                DatePicker taskDatePicker = new DatePicker() ;
                                AnchorPane.setLeftAnchor(taskDatePicker, 50.0);
                                AnchorPane.setTopAnchor(taskDatePicker, 110.0);
                                taskDatePicker.setPrefHeight(29);
                                taskDatePicker.setPrefWidth(0.1*TASK_LIST_WIDTH);

                                Button saveTaskBtn = new Button("Save") ;
                                AnchorPane.setLeftAnchor(saveTaskBtn, 50.0);
                                AnchorPane.setTopAnchor(saveTaskBtn, 150.0);
                                saveTaskBtn.setPrefHeight(35);
                                saveTaskBtn.setPrefWidth(0.1*TASK_LIST_WIDTH);
                                saveTaskBtn.setFont(Font.font(13));

                                Button cancelBtn = new Button("Cancel") ;
                                AnchorPane.setLeftAnchor(cancelBtn,159.0);
                                AnchorPane.setTopAnchor(cancelBtn, 150.0);

                                cancelBtn.setPrefWidth(0.085*TASK_LIST_WIDTH);
                                cancelBtn.setPrefHeight(35);

                                Button setPriorityBtn = new Button() ;
                                ImageView priorityImage = new ImageView("file:micro_wins/src/main/resources/images/priority-" + task.getTaskPriority()+ "-icon.png") ;
                                priorityImage.setFitWidth(25);
                                priorityImage.setFitHeight(25);
                                setPriorityBtn.setGraphic(priorityImage);
                                AnchorPane.setRightAnchor(setPriorityBtn, 98.0);
                                AnchorPane.setTopAnchor(setPriorityBtn, 110.0);

                                Button addReminderBtn = new Button() ;
                                ImageView reminderImage = new ImageView("file:micro_wins/src/main/resources/images/alarm-icon.png") ;
                                reminderImage.setFitWidth(25);
                                reminderImage.setFitHeight(25);
                                addReminderBtn.setGraphic(reminderImage);
                                AnchorPane.setRightAnchor(addReminderBtn, 50.0);
                                AnchorPane.setTopAnchor(addReminderBtn, 110.0);

                                editTaskRoot.getChildren().addAll(taskNameTxt, taskDescriptionTxt, taskDatePicker, bottomSep, saveTaskBtn, cancelBtn, setPriorityBtn, addReminderBtn) ;
                                setGraphic(editTaskRootContainer);

                                cancelBtn.setOnMouseClicked(cancelEvent -> {
                                    taskOnTodayRoot.getChildren().add(bottomSep) ;
                                    
                                    setGraphic(taskOnTodayRoot);
                                });

                            });
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
