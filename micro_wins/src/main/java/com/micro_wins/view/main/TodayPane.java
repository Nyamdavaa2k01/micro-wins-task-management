package com.micro_wins.view.main;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 06/05/2022 - 1:46 AM
 */

import com.micro_wins.constants.ConstantColors;

import com.micro_wins.constants.ConstantDictionaryValues;
import com.micro_wins.constants.ConstantStyles;
import com.micro_wins.constants.Functions;
import com.micro_wins.model.Project;
import com.micro_wins.model.Result;
import com.micro_wins.model.Task;
import com.micro_wins.repository.ProjectRepo;
import com.micro_wins.repository.ResultRepo;
import com.micro_wins.repository.TaskRepo;
import com.micro_wins.view.FxController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
@FxmlView
public class  TodayPane implements Initializable, FxController {

    ConstantColors constantColors ;
    ConstantStyles constantStyles ;
    ConstantDictionaryValues constantDictionaryValues ;

    @Autowired
    TaskRepo taskRepo ;

    @Autowired
    ProjectRepo projectRepo ;

    @Autowired
    ResultRepo resultRepo ;

    @FXML
    private ListView<Task> taskList;

    Stage priorityButtonsStage ;
    Stage projectButtonsStage ;
    int priority ;
    Project chosenProject ;

    public void refreshTaskList () {
        int i ;
        taskList.getItems().clear();
        for (i = 1 ; i <= 3 ; i ++) {
            List<Task> tasks = taskRepo.findByTaskStatus(i) ;
            tasks.forEach(eachTask -> {
                taskList.getItems().add(eachTask) ;
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        constantColors = new ConstantColors() ;
        constantStyles = new ConstantStyles() ;
        //final double TASK_LIST_WIDTH = Screen.getPrimary().getVisualBounds().getWidth()*0.8 - 200 ;
        constantDictionaryValues = new ConstantDictionaryValues() ;
        final double TASK_LIST_WIDTH = 1000;
        taskList.setPrefWidth(TASK_LIST_WIDTH);
        taskList.setStyle("-fx-border-color:white;");
        taskList.setFocusTraversable(false);
        taskList.setPadding(new Insets(20, 200, 0, 50));
        refreshTaskList();

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
                            priority = task.getTaskPriority() ;
                            AnchorPane taskOnTodayRoot = new AnchorPane() ;
                            taskOnTodayRoot.setPrefWidth(TASK_LIST_WIDTH-200);
                            taskOnTodayRoot.setPrefHeight(80);
                            Button finishTaskBtn = new Button() ;
                            AnchorPane.setLeftAnchor(finishTaskBtn, 50.0);
                            AnchorPane.setTopAnchor(finishTaskBtn, 18.0);
                            finishTaskBtn.setPadding(new Insets(7, 14, 7, 14));
                            finishTaskBtn.setShape(new Circle(5));

                            String finishTaskBtnBorderColor ;
                            if (task.getTaskPriority() == constantDictionaryValues.getTASK_PRIORITY_URGENT())
                                finishTaskBtnBorderColor = constantColors.getPRIORITY1_COLOR() ;
                            else if (task.getTaskPriority() == constantDictionaryValues.getTASK_PRIORITY_HIGH())
                                finishTaskBtnBorderColor = constantColors.getPRIORITY2_COLOR() ;
                            else if (task.getTaskPriority() == constantDictionaryValues.getTASK_PRIORITY_MEDIUM())
                                finishTaskBtnBorderColor = constantColors.getPRIORITY3_COLOR() ;
                            else
                                finishTaskBtnBorderColor = constantColors.getPRIORITY4_COLOR() ;

                            String finishTaskBtnBgColor = finishTaskBtnBorderColor + "40";
                            finishTaskBtn.setStyle(
                                    "-fx-background-color: " + finishTaskBtnBgColor +  "; " +
                                            "-fx-border-color :" +  finishTaskBtnBorderColor + "; " +
                                            "-fx-border-width : 4 ;"
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

                            Button deleteTaskBtn = new Button() ;
                            ImageView seeMoreImage = new ImageView(new Image("file:micro_wins/src/main/resources/images/delete-icon.png")) ;
                            seeMoreImage.setFitWidth(20);
                            seeMoreImage.setFitHeight(20);
                            deleteTaskBtn.setGraphic(seeMoreImage);

                            Button changeProjectBtn = new Button() ;
                            if (task.getTaskProTitle() != null) changeProjectBtn.setText(task.getTaskProTitle());
                            else changeProjectBtn.setText("Inbox");
                            AnchorPane.setRightAnchor(changeProjectBtn, 51.0 );
                            AnchorPane.setBottomAnchor(changeProjectBtn, 11.0);
                            changeProjectBtn.setPrefWidth(0.088*TASK_LIST_WIDTH);
                            changeProjectBtn.setPrefHeight(28) ;
                            changeProjectBtn.setStyle("-fx-border-color:gray;" +
                                    "-fx-border-radius:10; ");

                            editTaskButtons.getChildren().addAll(editTaskBtn, deleteTaskBtn) ;
                            taskOnTodayRoot.getChildren().addAll(finishTaskBtn, taskTitleLbl, taskDescriptionLbl, bottomSep, editTaskButtons, changeProjectBtn) ;
                            setGraphic(taskOnTodayRoot);

                            /**
                             * when user clicks button with an icon of pen, textfields show and user can change textfield
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
                                TextField taskTitleTxt = new TextField() ;
                                taskTitleTxt.setText(task.getTaskTitle());
                                AnchorPane.setLeftAnchor(taskTitleTxt, 50.0);
                                AnchorPane.setTopAnchor(taskTitleTxt, 10.0);
                                AnchorPane.setRightAnchor(taskTitleTxt, 50.0);
                                taskTitleTxt.setPrefHeight(35);

                                taskTitleTxt.setFont(Font.font("Times New Roman Bold", 18));

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
                                taskDatePicker.setValue(Functions.LOCALDATE_TO_DATE.dateToLocalDate(getItem().getTaskStartDate()));
                                AnchorPane.setLeftAnchor(taskDatePicker, 50.0);
                                AnchorPane.setTopAnchor(taskDatePicker, 110.0);
                                taskDatePicker.setPrefHeight(29);
                                taskDatePicker.setPrefWidth(0.1*TASK_LIST_WIDTH);

                                Button setProjectBtn = new Button(task.getTaskProTitle()) ;
                                setProjectBtn.setPrefWidth(85);
                                setProjectBtn.setStyle(constantStyles.getDEFAULT_BUTTON_STYLE());
                                AnchorPane.setLeftAnchor(setProjectBtn, 160.0);

                                AnchorPane.setTopAnchor(setProjectBtn, 110.0);
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

                                editTaskRoot.getChildren().addAll(taskTitleTxt, taskDescriptionTxt, taskDatePicker, setProjectBtn, bottomSep, saveTaskBtn, cancelBtn, setPriorityBtn, addReminderBtn) ;
                                setGraphic(editTaskRootContainer);

                                /**
                                 * Actions
                                 */

                                cancelBtn.setOnMouseClicked(cancelEvent -> {
                                    taskOnTodayRoot.getChildren().add(bottomSep) ;
                                    setGraphic(taskOnTodayRoot);
                                });

                                saveTaskBtn.setOnMouseClicked(editEvent -> {

                                    Optional<Task> optionalTask = taskRepo.findById(getItem().getTaskId());
                                    System.out.println(getItem().getTaskId());
                                    if (optionalTask.isPresent()) {
                                        Task editTask = optionalTask.get() ;
                                        editTask.setTaskTitle(taskTitleTxt.getText());
                                        editTask.setTaskDefinition(taskDescriptionTxt.getText());
                                        editTask.setTaskStartDate(Functions.LOCALDATE_TO_DATE.localDateToDate(taskDatePicker.getValue()));
                                        editTask.setTaskPriority(priority);
                                        if (chosenProject != null) {
                                            editTask.setTaskProId(chosenProject.getProId());
                                            editTask.setTaskProTitle(chosenProject.getProTitle());
                                        }
                                        taskRepo.save(editTask) ;
                                        refreshTaskList();
                                    }

                                });

                                setPriorityBtn.setOnMouseClicked(setPriorityEvent -> {
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
                                        priorityButtons[i].setStyle(constantStyles.getDEFAULT_BUTTON_STYLE());

                                        String buttonText = "Priority " + (i+1) ;
                                        String buttonGraphicFilePath ="file:micro_wins/src/main/resources/images/priority-"+(i+1)+"-icon.png" ;
                                        ImageView buttonGraphicImage = new ImageView(new Image(buttonGraphicFilePath)) ;
                                        buttonGraphicImage.setFitHeight(25);
                                        buttonGraphicImage.setFitWidth(25);
                                        priorityButtons[i].setText(buttonText);
                                        priorityButtons[i].setGraphic(buttonGraphicImage);
                                        priorityButtons[i].setPrefWidth(200);
                                        priorityButtons[i].setPrefHeight(43);
                                        priorityButtonsRoot.getChildren().add(priorityButtons[i]) ;

                                        priorityButtons[i].setOnMouseClicked(setEvent -> {
                                            String text = ((Button) setEvent.getSource()).getText();
                                            priority = Integer.parseInt(text.replaceAll("[^0-9]", "")) ;
                                            setPriorityBtn.setGraphic(((Button) setEvent.getSource()).getGraphic());
                                            ((Stage)((Button)setEvent.getSource()).getScene().getWindow()).close();
                                        });

                                    }
                                    priorityButtonsStage.setScene(priorityButtonsScene);
                                    priorityButtonsStage.show();
                                });

                                setProjectBtn.setOnMouseClicked(setProjectEvent -> {
                                    ListView<Project> projectListView = new ListView<>() ;
                                    List<Project> projectList = projectRepo.findProjectsByProOwner(11) ;
                                    VBox projectListRoot = new VBox( );
                                    projectList.forEach(eachProject -> {
                                        projectListView.getItems().add(eachProject) ;
                                     });

                                    projectListView.setPadding(new Insets(0));
                                    projectListView.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>() {
                                        @Override
                                        public ListCell<Project> call(ListView<Project> projectListView) {
                                            ListCell<Project> customProjectCell = new ListCell<Project>() {
                                                @Override
                                                public void updateItem(Project project, boolean empty) {
                                                    super.updateItem(project, empty);
                                                    if (empty) {
                                                        setGraphic(null);
                                                    }
                                                    else {
                                                        Button projectBtn = new Button(project.getProTitle()) ;
                                                        projectBtn.setPrefHeight(43);
                                                        projectBtn.setPrefWidth(200);
                                                        projectBtn.setStyle(constantStyles.getDEFAULT_BUTTON_STYLE());
                                                        projectBtn.setAlignment(Pos.TOP_LEFT);
                                                        setGraphic(projectBtn);
                                                        projectBtn.setOnMouseClicked(e -> {
                                                            Optional<Project> optionalProject = projectRepo.findById(getItem().getProId()) ;
                                                            if (optionalProject.isPresent()) {
                                                                chosenProject = optionalProject.get() ;
                                                                setProjectBtn.setText(chosenProject.getProTitle());
                                                                projectButtonsStage.close();
                                                            }
                                                        });
                                                    }
                                                }
                                            } ;
                                            customProjectCell.setStyle("-fx-padding:0px;");
                                            return customProjectCell ;
                                        }
                                    });
                                    projectListRoot.getChildren().add(projectListView) ;
                                    if (projectButtonsStage == null) {
                                        projectButtonsStage = new Stage();
                                        projectButtonsStage.initStyle(StageStyle.UNDECORATED);
                                        Bounds setProjectBtnBounds = setProjectBtn.localToScreen(setProjectBtn.getBoundsInLocal()) ;
                                        int projectButtonsStageInitPosX = (int) setProjectBtnBounds.getMinX();
                                        int projectButtonsStageInitPosY = (int) setProjectBtnBounds.getMaxY() ;
                                        projectButtonsStage.setX(projectButtonsStageInitPosX);
                                        projectButtonsStage.setY(projectButtonsStageInitPosY);
                                    }

                                    Scene projectButtonsScene = new Scene(projectListRoot, 200, projectList.size()*43) ;
                                    projectButtonsStage.setScene(projectButtonsScene);
                                    projectButtonsStage.show();
                                });
                            });

                            deleteTaskBtn.setOnMouseClicked(deleteEvent -> {
                                Optional<Task> optionalTask = taskRepo.findById(getItem().getTaskId());
                                if (optionalTask.isPresent()) {
                                    Task deleteTask = optionalTask.get() ;
                                    taskRepo.deleteById(deleteTask.getTaskId());
                                    refreshTaskList();
                                }
                            });

                            finishTaskBtn.setOnMouseClicked(finishEvent -> {
                                Optional<Task> optionalTask = taskRepo.findById(getItem().getTaskId()) ;
                                if (optionalTask.isPresent()) {
                                    Task finishTask = optionalTask.get() ;
                                    finishTask.setTaskStatus(constantDictionaryValues.getTASK_STATUS_COMPLETED()) ;
                                    Result result = new Result() ;
                                    result.setTaskId(finishTask.getTaskId());
                                    result.setTaskCompletionPercent(1);
                                    result.setTaskCompletedDate(Functions.LOCALDATE_TO_DATE.localDateToDate(LocalDate.now()));
                                    resultRepo.save(result) ;
                                    taskRepo.save(finishTask);
                                    refreshTaskList();
                                }
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
