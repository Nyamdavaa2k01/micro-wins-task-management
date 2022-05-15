package com.micro_wins.view.main;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.micro_wins.constants.Functions;
import com.micro_wins.model.DailyTasks;
import com.micro_wins.model.Task;
import com.micro_wins.view.FxController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import net.rgielen.fxweaver.core.FxmlView;
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
                        HBox test = new HBox( );
                        Label title = new Label(Functions.DATE_TO_STRING.dateToString(dailyTasks.getDayDate())) ;
                        Button testBtn= new Button("ssda") ;
                        test.getChildren().addAll(title, testBtn) ;
                        setGraphic(test);
                    }
                };
                return customCell ;
            }
        });
    }

    @FXML
    public void initialize () {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleCellFactory();
        int cnt = 0 ;
        LocalDate currentLocalDate = LocalDate.now() ;
        while (cnt < 200) {
            Date currentDate = Functions.LOCALDATE_TO_DATE.localDateToDate(currentLocalDate) ;
            comingDaysListView.getItems().add(new DailyTasks( currentDate )) ;
            cnt ++ ;
        }
    }
}
