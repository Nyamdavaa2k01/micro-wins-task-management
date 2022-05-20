package com.micro_wins.view.main;

import com.micro_wins.constant.Functions;
import com.micro_wins.model.Result;
import com.micro_wins.model.Task;
import com.micro_wins.repository.ResultRepo;
import com.micro_wins.repository.TaskRepo;
import com.micro_wins.view.FxController;
import com.micro_wins.view.StageManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
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

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 18/05/2022 - 1:30 PM
 * @purpose Хэрэглэгчийн дуусгасан даалгавруудын тоон утга болон даалгавруудыг үзүүлэх interface
 * @definition Дуусгасан даалгавруудыг харуулахдаа ListView, харин тоон утгыг нь харуулахдаа
 * PieChart ашигласан болно.
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

    @FXML
    private ScrollPane scrollRoot ;

    @FXML
    private VBox root ;

    private final int TASK_LIST_WIDTH = 1000 ;
    private final int NAVBAR_HEIGHT = 40 ;
    private final int TASK_VIEW_HEIGHT = 100 ;
    private final int LOW_THRESHOLD_TASK_CNT = 3;
    private final int MEDIUM_THRESHOLD_TASK_CNT = 7 ;
    private final int HIGH_THRESHOLD_TASK_CNT = 10;
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
                            acceptTaskBtn.setDisable(true);

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

       Rectangle2D screenBounds = Screen.getPrimary().getBounds() ;
       scrollRoot.setPrefHeight(screenBounds.getHeight()-NAVBAR_HEIGHT);
       finishedTaskList.setPrefHeight(finishedTaskList.getItems().size() * TASK_VIEW_HEIGHT);
       finishedTaskList.setMaxHeight(finishedTaskList.getItems().size()*TASK_VIEW_HEIGHT);

       handleBarChart();
    }

    private void handleBarChart () {
        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();
        final BarChart<Number,String> bc =
                new BarChart<>(xAxis,yAxis);
        xAxis.setTickLabelRotation(0);

        XYChart.Series<Number, String> series = new XYChart.Series();


        LocalDate currentDate = LocalDate.now().minusDays(6) ;
        final int[] completeCnt = {0,0,0,0,0,0,0} ;
        List<Result> allResult = resultRepo.findAll() ;
        allResult.forEach(result -> {
            int dayDiff = Math.toIntExact(DAYS.between(Functions.DATE_TO_LOCALDATE.dateToLocalDate(result.getTaskCompletedDate()), LocalDate.now()));
            if (dayDiff < 7) {
                completeCnt[dayDiff] ++ ;
            }
        });
        while(!currentDate.isAfter(LocalDate.now())) {
            String dateString ;
            if (currentDate.isEqual(LocalDate.now())) dateString = "Today" ;
            else if (currentDate.plusDays(1).isEqual(LocalDate.now())) dateString = "Yesterday" ;
            else dateString = Functions.DATE_TO_STRING.dateToString(Functions.LOCALDATE_TO_DATE.localDateToDate(currentDate)) ;
            int dayDiff = (int) DAYS.between(currentDate, LocalDate.now());
            final XYChart.Data<Number, String> data = new XYChart.Data<>(completeCnt[dayDiff], dateString) ;
            data.nodeProperty().addListener(new ChangeListener<Node>() {

                @Override
                public void changed(ObservableValue<? extends Node> observableValue, Node oldNode, Node newNode) {
                    if(newNode != null) {
                        int value = (int) data.getXValue();
                        String barColor ;
                        if (value <= LOW_THRESHOLD_TASK_CNT) {
                            barColor = "#81E771" ;
                        }
                        else if (value <= MEDIUM_THRESHOLD_TASK_CNT) {
                            barColor = "#55BD45" ;
                        }
                        else if(value < HIGH_THRESHOLD_TASK_CNT) {
                            barColor = "#3AA529" ;
                        }
                        else {
                            barColor = "#197909" ;
                        }
                        newNode.setStyle("-fx-bar-fill:" + barColor + ";");
                    }
                }


            });
            series.getData().add(data) ;
            currentDate = currentDate.plusDays(1) ;
        }
        bc.getData().add(series) ;
        bc.setLegendVisible(false);
        bc.setAlternativeRowFillVisible(false);
        bc.setAlternativeColumnFillVisible(false);
        bc.setHorizontalGridLinesVisible(false);
        bc.setVerticalGridLinesVisible(false);

        bc.setMaxWidth(TASK_LIST_WIDTH);
        root.getChildren().add(bc) ;
    }
}