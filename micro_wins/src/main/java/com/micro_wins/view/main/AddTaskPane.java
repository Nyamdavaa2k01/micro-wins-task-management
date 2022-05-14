/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @definition Header дээрх "+" товчийг дарахад гарж ирэх ба хэрхэн цонх болгон үзүүлж буйг HeaderMenuController-оос харна уу.
 */

package com.micro_wins.view.main;

import com.micro_wins.constants.ConstantColors;
import com.micro_wins.constants.ConstantStyles;
import com.micro_wins.model.Task;
import com.micro_wins.repository.TaskRepo;
import com.micro_wins.utils.ApplicationContextProvider;
import com.micro_wins.view.FxController;
import com.micro_wins.view.StageManager;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

@Controller
@FxmlView
public class AddTaskPane implements Initializable, FxController {


    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    ConfigurableApplicationContext springAppContext;

    @Autowired
    TaskRepo taskRepo ;

    Stage addTaskStage ;
    private Stage priorityButtonsStage ;

    /**
     * priority 4 is choosen by default
     */
    private int priority = 4;
    ConstantStyles constantStyles ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        constantStyles = new ConstantStyles() ;
        taskDatePicker.setValue(LocalDate.now());
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
    public void initialize() {

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
        Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
        alert.setTitle("Мэдэгдэл");
        alert.setHeaderText("Энэхүү хэсгийн хөгжүүлэлт дуусаагүй байна.");
        alert.setContentText("Хугацаа сануулах үйлдэл нь байж болох шаардлага дунд багтсан байгааг анхаарна уу");
        alert.showAndWait();
    }

    @FXML
    void addTask(ActionEvent event) throws SQLException {
        String taskTitle = taskNameTxt.getText() ;
        String taskDefinition = taskDescriptionTxt.getText();
        Date datePickerDate = Date.from(taskDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()) ;

        Task task = new Task(taskTitle, taskDefinition, priority, 1, datePickerDate, 12) ;
        task.setTaskProId(1);
        task.setTaskProTitle("inbox");
        taskRepo.save(task) ;

        stageManager.closeSecondaryStage();
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
                priority = Integer.parseInt(text.replaceAll("[^0-9]", "")) ;
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
