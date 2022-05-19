package com.micro_wins.view.main;

import com.micro_wins.constant.Functions;
import com.micro_wins.holder.AddTaskHolder;
import com.micro_wins.holder.ProjectHolder;
import com.micro_wins.holder.UpcomingHolder;
import com.micro_wins.holder.UserHolder;
import com.micro_wins.model.DailyTasks;
import com.micro_wins.model.Result;
import com.micro_wins.repository.*;
import com.micro_wins.view.FxController;
import com.micro_wins.view.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 16/05/2022 - 2:57 AM
 */

@Controller
@FxmlView
public class UpcomingPane implements Initializable, FxController {


    @Lazy
    @Autowired
    StageManager stageManager ;

    @Autowired
    TaskRepo taskRepo ;

    @Autowired
    ProjectRepo projectRepo ;

    @Autowired
    ResultRepo resultRepo ;

    @Autowired
    ConfigurableApplicationContext springAppContext ;

    @FXML
    private ListView<DailyTasks> comingDaysListView;

    @FXML
    private DatePicker setDateDatePicker;

    @FXML
    void setDate(ActionEvent event) {
        LocalDate chosenDate = setDateDatePicker.getValue() ;
        int dateIdx = (int) DAYS.between(LocalDate.now(), chosenDate);
        Date upperDate = Functions.LOCALDATE_TO_DATE.localDateToDate(LocalDate.now().plusDays(200)) ;
        if (dateIdx < 0 || dateIdx > 200) {
            Functions.INFORMATION_ALERT.informationAlert("Огноо алдаатай ",
                    "Хайх огноо нь өнөөдрөөс (" + Functions.DATE_TO_STRING.dateToString(upperDate) + ") хооронд байхыг анхаарна уу", "Ok");
            return;
        }
        comingDaysListView.scrollTo(dateIdx);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stageManager = springAppContext.getBean(StageManager.class);
        UpcomingHolder.getInstance().setUpcomingPane(this);
        setDateDatePicker.getEditor().setEditable(false);
        handleCellFactory();
        refreshComingDaysListView();
    }

    public void refreshComingDaysListView () {
        setDateDatePicker.setValue(LocalDate.now());
        int cnt = 0 ;
        LocalDate currentLocalDate = LocalDate.now() ;
        /**
         * Show next 200 days in the listview
         */
        comingDaysListView.getItems().clear();
        while (cnt < 200) {
            Date currentDate = Functions.LOCALDATE_TO_DATE.localDateToDate(currentLocalDate) ;
            currentLocalDate = currentLocalDate.plusDays(1) ;
            DailyTasks dailyTasks = new DailyTasks() ;
            dailyTasks.setDayDate(currentDate);
            comingDaysListView.getItems().add(dailyTasks) ;
            cnt ++ ;
        }
    }

    private void handleCellFactory () {
        comingDaysListView.setCellFactory(new Callback<ListView<DailyTasks>, ListCell<DailyTasks>>() {
            @Override
            public ListCell<DailyTasks> call(ListView<DailyTasks> dailyTasksListView) {
                ListCell<DailyTasks> customCell = new ListCell<>() {
                    @Override
                    public void updateItem(DailyTasks dailyTasks, boolean empty) {
                        super.updateItem(dailyTasks, empty);
                        if (empty) {
                            setGraphic(null);
                            return ;
                        }
                        VBox cellRoot = new VBox();
                        cellRoot.setSpacing(10);
                        cellRoot.setPadding(new Insets(50, 50, 0, 50));

                        Label title = new Label(Functions.DATE_TO_STRING.dateToString(dailyTasks.getDayDate())) ;
                        title.setFont(Font.font(14));

                        Separator titleSep = new Separator() ;
                        titleSep.setStyle("-fx-background:black;");

                        dailyTasks.setTaskRepo(taskRepo);
                        dailyTasks.setProjectRepo(projectRepo);
                        dailyTasks.setResultRepo(resultRepo);
                        dailyTasks.setCalledFromUpcoming(true);

                        ListView innerList = dailyTasks.getInnerList() ;

                        innerList.prefHeightProperty().bind(dailyTasks.getInnerList().heightProperty());

                        VBox listContainer = new VBox();
                        dailyTasks.cellFactoryImpl();
                        dailyTasks.refreshTaskList();

                        /**
                         * addTaskBtn implementation is postponed (even possibly skipped)
                         */
                        Button addTaskBtn = new Button("Add Task") ;
                        addTaskBtn.setStyle("-fx-background-color:transparent;" +
                                "-fx-padding: 0 0 0 50;" +
                                "-fx-border-insets: 0 0 0 50;" +
                                "-fx-background-insets: 0 0 0 50;" +
                                "-fx-cursor:hand;");

                        addTaskBtn.setAlignment(Pos.TOP_LEFT);
                        String buttonGraphicFilePath ="file:micro_wins/src/main/resources/images/add-icon.png" ;
                        ImageView buttonGraphicImage = new ImageView(new Image(buttonGraphicFilePath)) ;
                        buttonGraphicImage.setFitHeight(25);
                        buttonGraphicImage.setFitWidth(25);
                        addTaskBtn.setGraphic(buttonGraphicImage);
                        addTaskBtn.setPrefSize(900, 50);

                        addTaskBtn.setOnMouseEntered(enterEvent -> {
                            addTaskBtn.setTextFill(Color.web("EE6A6A"));
                        });
                        addTaskBtn.setOnMouseExited(exitEvent -> {
                            addTaskBtn.setTextFill(Color.BLACK);
                        });
                        addTaskBtn.setOnMouseClicked(addEvent -> {
                            stageManager.addStage(AddTaskPane.class);
                            AddTaskHolder.getInstance().getAddTaskPane().setDate(dailyTasks.getDayDate());
                        });


                        listContainer.getChildren().add(dailyTasks.getInnerList()) ;
                        cellRoot.getChildren().addAll(title, titleSep, listContainer, addTaskBtn) ;
                        setGraphic(cellRoot);
                    }
                };
                return customCell ;
            }
        });
    }

}
