package com.micro_wins.model;

import com.micro_wins.constant.ConstantColors;
import com.micro_wins.constant.ConstantDictionaryValues;
import com.micro_wins.constant.ConstantStyles;
import com.micro_wins.constant.Functions;
import com.micro_wins.holder.UpcomingHolder;
import com.micro_wins.holder.UserHolder;
import com.micro_wins.repository.ProjectRepo;
import com.micro_wins.repository.ResultRepo;
import com.micro_wins.repository.TaskRepo;
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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 15/05/2022 - 5:58 PM
 * @purpose DailyTasks класс нь хийх даалгавруудын жагсаалтыг хэрэгжүүлэхэд ашиглагдана.
 * @definition Энэхүү класст хийх даалгавруудын жагсаалтыг хэрхэн яаж харуулах тухай cellFactory-г
 * хэрэгжүүлсэн. Мөн жагсаалт дахь даалгавруудтай харьцан тэдгээрийн утгыг өөрчлөх, устгах, дууссаныг
 * тэмдэглэх боломжуудыг хэрэгжүүлсэн болно.
 */

@Component
public class DailyTasks{

    ConstantColors constantColors ;
    ConstantStyles constantStyles ;
    ConstantDictionaryValues constantDictionaryValues ;

    TaskRepo taskRepo ;
    ProjectRepo projectRepo ;
    ResultRepo resultRepo ;

    Date dayDate ;
    ListView<Task> taskList ;

    Stage priorityButtonsStage ;
    Stage projectButtonsStage ;

    private int priority ;
    Project chosenProject = null;
    Boolean sortedByDate = false;
    Boolean sortedByPriority = false ;
    private Boolean isCalledFromUpcoming = false ;


    public void setCalledFromUpcoming(Boolean calledFromUpcoming) {
        isCalledFromUpcoming = calledFromUpcoming;
    }

    @Autowired
    public void setTaskRepo (TaskRepo taskRepo) {
        this.taskRepo = taskRepo ;
    }

    @Autowired
    public void setProjectRepo (ProjectRepo projectRepo) {
        this.projectRepo = projectRepo ;
    }

    @Autowired
    public void setResultRepo (ResultRepo resultRepo) {
        this.resultRepo = resultRepo ;
    }

    private void refreshParentController () {
        if (isCalledFromUpcoming) UpcomingHolder.getInstance().getUpcomingPane().refreshComingDaysListView();
    }

    private final int TASK_LIST_WIDTH = 950;

    public void refreshTaskList () {
        List<Task> tasks ;
        // before code tuning
        tasks = taskRepo.findAll() ;
        for (Task task : tasks) {
            if(dayDate != null) {
                if (task.getTaskStartDate() == dayDate) taskList.getItems().add(task) ;
                else continue;
            }
            else {
                taskList.getItems().add(task) ;
            }
        }

        // after code tuning
        if (dayDate != null) tasks = taskRepo.findByTaskStartDate(dayDate) ;
        else tasks = taskRepo.findAll() ;
        taskList.getItems().clear();
        tasks.forEach(eachTask -> {
            if (eachTask.getTaskStatus() != constantDictionaryValues.getTASK_STATUS_COMPLETED()) {
                taskList.getItems().add(eachTask) ;
            }
        });


        taskList.setPrefHeight(taskList.getItems().size() * 100);


    }

    public void cellFactoryImpl() {
        /**
         * Implementation of cellFactory of innerlist is defined here.
         * DailyTasks class is used in UpcomingPane, so the cellFactory of innerlist has important value for
         * UpcomingPane. 
         */
        taskList = new ListView<>( );
        taskList.setPrefWidth(TASK_LIST_WIDTH);
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
                            taskOnTodayRoot.setPrefHeight(90);

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

                            Button acceptTaskBtn = new Button() ;
                            AnchorPane.setLeftAnchor(acceptTaskBtn, 43.0);
                            AnchorPane.setTopAnchor(acceptTaskBtn, 15.0) ;
                            ImageView acceptImage = new ImageView(new Image("file:micro_wins/src/main/resources/images/accepted-icon.png")) ;
                            acceptImage.setFitWidth(28);
                            acceptImage.setFitHeight(28);
                            acceptTaskBtn.setGraphic(acceptImage);
                            acceptTaskBtn.setVisible(false);

                            Text taskTitleLbl = new Text(task.getTaskTitle()) ;
                            AnchorPane.setLeftAnchor(taskTitleLbl, 113.0);
                            AnchorPane.setTopAnchor(taskTitleLbl, 14.0);
                            AnchorPane.setRightAnchor(taskTitleLbl, 50.0);
                            taskTitleLbl.setFont(Font.font(18));
                            if (task.getTaskStatus() == constantDictionaryValues.getTASK_STATUS_COMPLETED()) taskTitleLbl.setStrikethrough(true);

                            Label taskDescriptionLbl = new Label(task.getTaskDefinition()) ;
                            AnchorPane.setLeftAnchor(taskDescriptionLbl, 113.0);
                            AnchorPane.setBottomAnchor(taskDescriptionLbl, 30.0);
                            AnchorPane.setRightAnchor(taskDescriptionLbl, 50.0);
                            taskDescriptionLbl.setTextFill(Paint.valueOf("#4d4a4a"));
                            taskDescriptionLbl.setFont(Font.font("Times New Roman", 12));

                            Label dateLbl = new Label() ;
                            dateLbl.setText(Functions.DATE_TO_STRING.dateToString(task.getTaskStartDate()));
                            Date todayDate = Functions.LOCALDATE_TO_DATE.localDateToDate(LocalDate.now()) ;
                            if (task.getTaskStartDate().compareTo(todayDate) == 0) {
                                dateLbl.setText("Today");
                                dateLbl.setTextFill(Color.GREEN);
                            }
                            else if (task.getTaskStartDate().compareTo(todayDate) == -1) {
                                dateLbl.setTextFill(Color.RED);
                            }

                            AnchorPane.setLeftAnchor(dateLbl, 113.0);
                            AnchorPane.setBottomAnchor(dateLbl, 11.0) ;

                            Separator bottomSep = new Separator() ;
                            AnchorPane.setLeftAnchor(bottomSep, 50.0);
                            AnchorPane.setBottomAnchor(bottomSep, 5.0);
                            AnchorPane.setRightAnchor(bottomSep, 50.0);

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

                            Label changeProjectLbl = new Label() ;
                            if (task.getTaskProTitle() != null) changeProjectLbl.setText(task.getTaskProTitle());
                            else changeProjectLbl.setText("Inbox");
                            AnchorPane.setRightAnchor(changeProjectLbl, 51.0 );
                            AnchorPane.setBottomAnchor(changeProjectLbl, 11.0);
                            changeProjectLbl.setPrefWidth(0.088*TASK_LIST_WIDTH);
                            changeProjectLbl.setAlignment(Pos.CENTER);
                            changeProjectLbl.setTextFill(Color.BLUE);

                            editTaskButtons.getChildren().addAll(editTaskBtn, deleteTaskBtn) ;
                            taskOnTodayRoot.getChildren().addAll(finishTaskBtn, acceptTaskBtn, taskTitleLbl, taskDescriptionLbl, dateLbl, bottomSep, editTaskButtons, changeProjectLbl) ;
                            setGraphic(taskOnTodayRoot);

                            /**
                             * when user clicks button with an icon of pen, main view of task in the listview is changed
                             * so that user can edit the task directly from the listview instead of calling another modal.
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

                                double collapsedHeight = taskList.getHeight() ;
                                taskList.setPrefHeight(collapsedHeight + 104);

                                /**
                                 * Implementation of actions that will be done after certain buttons are clicked
                                 */
                                cancelBtn.setOnMouseClicked(cancelEvent -> {
                                    taskOnTodayRoot.getChildren().add(bottomSep) ;
                                    setGraphic(taskOnTodayRoot);
                                    double expandedHeight = taskList.getHeight();
                                    taskList.setPrefHeight(expandedHeight - 104);
                                });

                                saveTaskBtn.setOnMouseClicked(editEvent -> {
                                    Optional<Task> optionalTask = taskRepo.findById(getItem().getTaskId());
                                    System.out.println(getItem().getTaskId());
                                    if (optionalTask.isPresent()) {
                                        Task editTask = optionalTask.get() ;
                                        editTask.setTaskTitle(taskTitleTxt.getText());
                                        editTask.setTaskDefinition(taskDescriptionTxt.getText());
                                        Date previousDate = editTask.getTaskStartDate() ;
                                        editTask.setTaskStartDate(Functions.LOCALDATE_TO_DATE.localDateToDate(taskDatePicker.getValue()));

                                        if (editTask.getTaskStartDate() != previousDate) {
                                               refreshParentController();
                                        }

                                        editTask.setTaskPriority(priority);
                                        if (chosenProject != null) {
                                            editTask.setTaskProId(chosenProject.getProId());
                                            editTask.setTaskProTitle(chosenProject.getProTitle());
                                        }
                                        taskRepo.save(editTask) ;
                                        taskOnTodayRoot.getChildren().add(bottomSep) ;
                                        setGraphic(taskOnTodayRoot);
                                        double expandedHeight = taskList.getHeight();
                                        taskList.setPrefHeight(expandedHeight - 104);
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
                                    List<Project> projectList = projectRepo.findProjectsByProOwner(UserHolder.getInstance().getUser().getUserId()) ;
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
                                    if (Functions.DELETE_CONFIRM_ALERT.deleteConfirmAlert("Confirm Delete", "Are you sure you want to delete the task " +
                                            deleteTask.getTaskTitle(), "yes", "no")) {
                                        taskRepo.deleteById(deleteTask.getTaskId());
                                        refreshTaskList();
                                    }
                                }
                            });

                            finishTaskBtn.setOnMouseEntered(enterEvent -> {
                                finishTaskBtn.setVisible(false);
                                acceptTaskBtn.setVisible(true);
                            });

                            acceptTaskBtn.setOnMouseExited(exitEvent -> {
                                finishTaskBtn.setVisible(true);
                                acceptTaskBtn.setVisible(false);
                            });

                            acceptTaskBtn.setOnMouseClicked(finishEvent -> {
                                Optional<Task> optionalTask = taskRepo.findById(getItem().getTaskId()) ;
                                if (optionalTask.isPresent()) {
                                    Task finishTask = optionalTask.get() ;
                                    finishTask.setTaskStatus(constantDictionaryValues.getTASK_STATUS_COMPLETED()) ;
                                    taskRepo.save(finishTask);
                                    Result result = new Result() ;
                                    result.setTaskId(finishTask.getTaskId());
                                    result.setTaskCompletionPercent(1);
                                    result.setTaskCompletedDate(Functions.LOCALDATE_TO_DATE.localDateToDate(LocalDate.now()));
                                    resultRepo.save(result) ;
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

    public DailyTasks () {
        constantColors = new ConstantColors() ;
        constantStyles = new ConstantStyles() ;
        constantDictionaryValues = new ConstantDictionaryValues() ;
        taskList = new ListView<>();
        taskList.setStyle("-fx-padding:0;");
    }

    public void sortByTime() {
        if (!sortedByDate) {
            List<Task> sortTasks = taskRepo.findByOrderByTaskStartDateAsc() ;
            taskList.getItems().clear();
            sortTasks.forEach(task -> {
                if (task.getTaskStatus() != constantDictionaryValues.getTASK_STATUS_COMPLETED()) {
                    taskList.getItems().add(task) ;
                }
            });
            sortedByDate = true ;
        }
        else {
            refreshTaskList(); ;
            sortedByDate = false ;
        }
    }

    public void sortByPriority() {
        if (!sortedByPriority) {
            List<Task> sortTasks = taskRepo.findByOrderByTaskPriorityAsc() ;
            taskList.getItems().clear();
            sortTasks.forEach(task -> {
                if (task.getTaskStatus() != 4)
                    taskList.getItems().add(task) ;
            });
            sortedByPriority = true ;
        }
        else {
            refreshTaskList();
            sortedByPriority = false ;
        }
    }

    public Date getDayDate() {
        return dayDate;
    }

    public void setDayDate(Date dayDate) {
        this.dayDate = dayDate;
    }

    public ListView<Task> getInnerList() {
        return taskList;
    }


}
