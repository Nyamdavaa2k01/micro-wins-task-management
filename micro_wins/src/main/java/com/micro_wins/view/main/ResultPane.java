package com.micro_wins.view.main;

import com.micro_wins.constant.Functions;
import com.micro_wins.holder.UserHolder;
import com.micro_wins.model.Project;
import com.micro_wins.model.Result;
import com.micro_wins.model.Task;
import com.micro_wins.repository.ResultRepo;
import com.micro_wins.repository.TaskRepo;
import com.micro_wins.view.FxController;
import com.micro_wins.view.StageManager;
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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 18/05/2022 - 1:30 PM
 */


@Controller
@FxmlView
public class ResultPane implements Initializable, FxController {
    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    TaskRepo taskRepo ;

    @Autowired
    ResultRepo resultRepo ;

    @FXML
    private ListView<Task> finishedTaskList;

    private int TASK_LIST_WIDTH = 1000 ;
    private void handleCellFactory () {
        finishedTaskList.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
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
                            AnchorPane finishedTaskRoot = new AnchorPane( );
                            finishedTaskRoot.setPrefHeight(70);

                            Button acceptTaskBtn = new Button() ;
                            AnchorPane.setLeftAnchor(acceptTaskBtn, 43.0);
                            AnchorPane.setTopAnchor(acceptTaskBtn, 15.0) ;
                            ImageView acceptImage = new ImageView(new Image("file:micro_wins/src/main/resources/images/accepted-icon.png")) ;
                            acceptImage.setFitWidth(28);
                            acceptImage.setFitHeight(28);
                            acceptTaskBtn.setGraphic(acceptImage);

                            Text taskTitleText = new Text(task.getTaskTitle()) ;
                            AnchorPane.setLeftAnchor(taskTitleText, 113.0);
                            AnchorPane.setTopAnchor(taskTitleText, 14.0);
                            AnchorPane.setRightAnchor(taskTitleText, 50.0);
                            taskTitleText.setFont(Font.font(18));
                            taskTitleText.setStrikethrough(true);

                            Label taskDescriptionLbl = new Label(task.getTaskDefinition()) ;
                            AnchorPane.setLeftAnchor(taskDescriptionLbl, 113.0);
                            AnchorPane.setBottomAnchor(taskDescriptionLbl, 10.0);
                            AnchorPane.setRightAnchor(taskDescriptionLbl, 50.0);
                            taskDescriptionLbl.setTextFill(Paint.valueOf("#4d4a4a"));
                            taskDescriptionLbl.setFont(Font.font("Times New Roman", 12));

                            Separator bottomSep = new Separator() ;
                            AnchorPane.setLeftAnchor(bottomSep, 50.0);
                            AnchorPane.setBottomAnchor(bottomSep, 5.0);
                            AnchorPane.setRightAnchor(bottomSep, 50.0);

                            finishedTaskRoot.getChildren().addAll(acceptTaskBtn, taskTitleText, taskDescriptionLbl, bottomSep) ;
                            setGraphic(finishedTaskRoot);
                        }
                    }
                };
                return customTaskCell;
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       handleCellFactory();
       List<Result> resultList = resultRepo.findByTaskCompletedDate(Functions.LOCALDATE_TO_DATE.localDateToDate(LocalDate.now())) ;
       resultList.forEach(result -> {
           Optional<Task> optionalTask = taskRepo.findById(result.getTaskId()) ;
           if (optionalTask.isPresent()) {
               finishedTaskList.getItems().add(optionalTask.get()) ;
           }
       });
        /**
         * TODO scrollpane
         */
       finishedTaskList.setPrefHeight(finishedTaskList.getItems().size() * 330);
       finishedTaskList.setMaxHeight(finishedTaskList.getItems().size()*330);
       System.out.println(finishedTaskList);
    }
}
