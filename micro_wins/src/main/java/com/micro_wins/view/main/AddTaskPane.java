/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @definition Header дээрх "+" товчийг дарахад гарж ирэх ба хэрхэн цонх болгон үзүүлж буйг HeaderMenuController-оос харна уу.
 */

package com.micro_wins.view.main;

import com.micro_wins.constant.ConstantColors;
import com.micro_wins.constant.ConstantStyles;
import com.micro_wins.constant.Functions;
import com.micro_wins.model.Project;
import com.micro_wins.model.Task;
import com.micro_wins.repository.ProjectRepo;
import com.micro_wins.repository.TaskRepo;
import com.micro_wins.view.FxController;
import com.micro_wins.view.StageManager;
import javafx.beans.binding.BooleanBinding;
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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.Date;

@Controller
@FxmlView
public class AddTaskPane implements Initializable, FxController {


    @Lazy
    @Autowired
    private StageManager stageManager;

    private Functions informationAlert;

    @Autowired
    ConfigurableApplicationContext springAppContext;

    @Autowired
    TaskRepo taskRepo ;

    @Autowired
    ProjectRepo projectRepo ;

    private Stage priorityButtonsStage ;
    Stage projectButtonsStage ;

    /**
     * task values are being set from 4 main different places.
     * taskUserId and taskStatus are set by default inside the initialize function,
     * taskTitle, taskDefinition and taskStartDate are set by textfields when user clicks save button,
     * taskPriority is set by priority stage if not set the value is 4 by default,
     * taskProId and taskProTitle are set by project stage, if not set, project is inbox by default
     */

    Task task ;
    ConstantStyles constantStyles ;
    ConstantColors constantColors ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        constantStyles = new ConstantStyles() ;
        constantColors = new ConstantColors() ;
        informationAlert = Functions.INFORMATION_ALERT;
        taskDatePicker.setValue(LocalDate.now());
        task = new Task();
        /**
         * task default values
         */
        task.setTaskPriority(4);
        task.setTaskStatus(1);
        task.setTaskUserId(12);
        task.setTaskProId(1);
        task.setTaskProTitle("inbox");
        /**
         * Add Task Button is disabled while Task Name TextField is empty, and as soon as user start
         * typing task title, button is enabled
         */
        BooleanBinding bindTaskNameTxt = new BooleanBinding() {
            {
                super.bind(taskNameTxt.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return (taskNameTxt.getText().isEmpty());
            }
        };
        addTaskBtn.disableProperty().bind(bindTaskNameTxt);
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

        informationAlert.informationAlert("Information", "Sorry, The development of this section is not complete.", "OK");

    }

    @FXML
    void addTask(ActionEvent event) {
        String taskTitle = taskNameTxt.getText() ;
        String taskDefinition = taskDescriptionTxt.getText();
        Date datePickerDate = Date.from(taskDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()) ;
        task.setTaskTitle(taskTitle);
        task.setTaskDefinition(taskDefinition);
        task.setTaskStartDate(datePickerDate);
        taskRepo.save(task) ;
        stageManager.closeSecondaryStage();

        /**
         * Go to the latest scene
         */
        Class<? extends FxController> fxControllerClass = stageManager.getLatestFxControllerClass() ;
        if (fxControllerClass != null) stageManager.rebuildStage(fxControllerClass);
     }

    @FXML
    void cancel(ActionEvent event) {
        stageManager.closeSecondaryStage();
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
            priorityButtons[i].setStyle(constantStyles.getDEFAULT_BUTTON_STYLE());
            priorityButtons[i].setOnMouseClicked(e -> {
                String text = ((Button) e.getSource()).getText();
                int priority = Integer.parseInt(text.replaceAll("[^0-9]", "")) ;
                task.setTaskPriority(priority);
                setPriorityBtn.setGraphic(((Button) e.getSource()).getGraphic());
                ((Stage)((Button)e.getSource()).getScene().getWindow()).close();
            });
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
        }
        priorityButtonsStage.setScene(priorityButtonsScene);
        priorityButtonsStage.initModality(Modality.APPLICATION_MODAL);
        priorityButtonsStage.show();
    }

    /**
     * The following method (setProject) had been used to set project of the task when
     * user click set priority button.
     *
     * User cannot add tasks to project from private space so the following code will not be used.
     * However, the code is only commented but not deleted for possible future use in project tasks!
     */

    @FXML
    private Button setProjectBtn;

    @FXML
    void setProject(ActionEvent event) {
        ListView<Project> projectListView = new ListView<>() ;
        List<Project> projectList = projectRepo.findProjectsByProOwner(11) ;
        VBox projectListRoot = new VBox();
        projectList.forEach(project -> {
            projectListView.getItems().add(project) ;
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
                                    Project chosenProject = optionalProject.get() ;
                                    task.setTaskProTitle(chosenProject.getProTitle());
                                    task.setTaskProId(chosenProject.getProId());
                                    setProjectBtn.setText(task.getTaskProTitle());
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
        projectButtonsStage.initModality(Modality.APPLICATION_MODAL);
        projectButtonsStage.show();
    }

}
