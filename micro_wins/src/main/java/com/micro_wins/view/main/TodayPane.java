package com.micro_wins.view.main;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 06/05/2022 - 1:46 AM
 */

import com.micro_wins.constant.ConstantColors;

import com.micro_wins.constant.ConstantDictionaryValues;
import com.micro_wins.constant.ConstantStyles;
import com.micro_wins.constant.Functions;
import com.micro_wins.model.DailyTasks;
import com.micro_wins.model.Task;
import com.micro_wins.repository.*;
import com.micro_wins.view.FxController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

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

    @Autowired
    DictTypeRepo dictTypeRepo;

    @Autowired
    DictRepo dictRepo ;


    Stage chooseFilterOptionStage ;

    private int filterOpt = 0 ;

    DailyTasks dailyTasks;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        constantColors = new ConstantColors() ;
        constantStyles = new ConstantStyles() ;
        todayDateLbl.setText(Functions.TODAY_DATE_TO_STRING.todayDateToString());
        constantDictionaryValues = new ConstantDictionaryValues() ;
        dailyTasks = new DailyTasks();
        dailyTasks.setDayDate(Functions.LOCALDATE_TO_DATE.localDateToDate(LocalDate.now()));
        dailyTasks.setTaskRepo(taskRepo);
        dailyTasks.setProjectRepo(projectRepo);
        dailyTasks.setResultRepo(resultRepo);
        dailyTasks.cellFactoryImpl();
        dailyTasks.refreshTaskList();
        taskListRoot.getChildren().add(dailyTasks.getInnerList()) ;
    }

    @FXML
    private Button setFilterOptionBtn;

    @FXML
    private Button setSortOptionBtn;

    @FXML
    private VBox taskListRoot;

    @FXML
    private Label todayDateLbl;

    @FXML
    void setFilterOption(ActionEvent event) {
        VBox setFilterOptionsRoot = new VBox() ;
        Scene setFilterOptionsScene = new Scene(setFilterOptionsRoot, 200, 215) ;
        if (chooseFilterOptionStage != null) chooseFilterOptionStage.close();
        chooseFilterOptionStage = new Stage( );
        chooseFilterOptionStage.initStyle(StageStyle.UNDECORATED);

        /**
         * Changing the initial position of priorityButtonsStage relative to setPriorityBtn
         */
        Bounds setFilterOptionBtnBounds = setFilterOptionBtn.localToScreen(setFilterOptionBtn.getBoundsInLocal()) ;
        int priorityButtonsStageInitPosX = (int) setFilterOptionBtnBounds.getMinX();
        int priorityButtonsStageInitPosY = (int) setFilterOptionBtnBounds.getMaxY() ;
        chooseFilterOptionStage.setX(priorityButtonsStageInitPosX);
        chooseFilterOptionStage.setY(priorityButtonsStageInitPosY);


        Button [] filterOptions = new Button[5] ;
        int i ;
        for (i = 0 ; i < 5 ; i ++) {
            filterOptions[i] = new Button() ;
            filterOptions[i].setAlignment(Pos.TOP_LEFT);
            filterOptions[i].setStyle(constantStyles.getDEFAULT_BUTTON_STYLE());
            filterOptions[i].setOnMouseClicked(e -> {
                String text = ((Button) e.getSource()).getText();
                if (text.equals("No Filter")) {
                    filterOpt = 0 ;
                }
                else {
                    filterOpt = Integer.parseInt(text.replaceAll("[^0-9]", "")) + 6;
                }
                chooseFilterOptionStage.close();
                if (filterOpt == 0) dailyTasks.refreshTaskList();
                else {
                    List<Task> filteredTasks = taskRepo.findByTaskPriorityAndTaskStatus(filterOpt, constantDictionaryValues.getTASK_STATUS_OPEN()) ;
                    dailyTasks.getInnerList().getItems().clear();
                    filteredTasks.forEach(each -> {
                        Date todayDate = Functions.LOCALDATE_TO_DATE.localDateToDate(LocalDate.now()) ;
                        if(each.getTaskStartDate().compareTo(todayDate) == 0)
                            dailyTasks.getInnerList().getItems().add(each) ;
                    });

                }

            });
            String buttonText = "No Filter" ;
            String buttonGraphicFilePath ="file:micro_wins/src/main/resources/images/default-icon.png" ;
            if (i < 4) {
                buttonText = "Priority " + (i+1) ;
                buttonGraphicFilePath ="file:micro_wins/src/main/resources/images/priority-"+(i+1)+"-icon.png" ;
            }
            ImageView buttonGraphicImage = new ImageView(new Image(buttonGraphicFilePath)) ;
            buttonGraphicImage.setFitHeight(25);
            buttonGraphicImage.setFitWidth(25);
            filterOptions[i].setText(buttonText);
            filterOptions[i].setGraphic(buttonGraphicImage);
            filterOptions[i].setPrefWidth(200);
            filterOptions[i].setPrefHeight(43);
            setFilterOptionsRoot.getChildren().add(filterOptions[i]) ;
        }
        chooseFilterOptionStage.setScene(setFilterOptionsScene);
        chooseFilterOptionStage.show();
    }

    @FXML
    void setSortOption(ActionEvent event) {
        dailyTasks.sortByPriority();
    }

}
