package com.micro_wins.view.main;

import com.micro_wins.constant.Functions;
import com.micro_wins.model.DailyTasks;
import com.micro_wins.model.Task;
import com.micro_wins.repository.TaskRepo;
import com.micro_wins.view.FxController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 16/05/2022 - 2:57 AM
 */

@Controller
@FxmlView
public class UpcomingPane implements Initializable, FxController {

    @Autowired
    TaskRepo taskRepo ;

    @FXML
    private ListView<DailyTasks> comingDaysListView;

    @FXML
    private DatePicker setDateDatePicker;


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
                        AnchorPane cellRoot = new AnchorPane();

                        Label title = new Label(Functions.DATE_TO_STRING.dateToString(dailyTasks.getDayDate())) ;
                        title.setFont(Font.font(14));
                        AnchorPane.setTopAnchor(title, 25.0);
                        AnchorPane.setLeftAnchor(title, 50.0);

                        Separator titleSep = new Separator() ;
                        titleSep.setStyle("-fx-background:black;");
                        AnchorPane.setTopAnchor(titleSep, 45.0);
                        AnchorPane.setLeftAnchor(titleSep, 50.0);
                        AnchorPane.setRightAnchor(titleSep, 50.0);

                        List<Task> tasksForDay = taskRepo.findByTaskStartDate(dailyTasks.getDayDate()) ;
                        tasksForDay.forEach(each -> {
                            dailyTasks.addTask(each);
                        });
                        ListView innerList = dailyTasks.getInnerList() ;
                        AnchorPane.setTopAnchor(innerList, 65.0);
                        AnchorPane.setLeftAnchor(innerList, 50.0);
                        AnchorPane.setRightAnchor(innerList, 50.0);

                        cellRoot.getChildren().addAll(title, titleSep, innerList) ;
                        setGraphic(cellRoot);
                    }
                };
                return customCell ;
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleCellFactory();
        setDateDatePicker.setValue(LocalDate.now());
        int cnt = 0 ;
        LocalDate currentLocalDate = LocalDate.now() ;
        while (cnt < 200) {
            Date currentDate = Functions.LOCALDATE_TO_DATE.localDateToDate(currentLocalDate) ;
            currentLocalDate = currentLocalDate.plusDays(1) ;
            comingDaysListView.getItems().add(new DailyTasks( currentDate )) ;
            cnt ++ ;
        }
    }
}
