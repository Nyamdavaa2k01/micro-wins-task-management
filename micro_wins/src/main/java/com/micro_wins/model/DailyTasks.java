package com.micro_wins.model;

import com.micro_wins.repository.TaskRepo;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 16/05/2022 - 3:29 AM
 */

public class DailyTasks {

    Date dayDate ;
    ListView<Task> innerList ;

    public void cellFactoryImpl() {
        /**
         * Implementation of cellFactory of innerlist is defined here.
         * DailyTasks class is used in UpcomingPane, so the cellFactory of innerlist has important value for
         * UpcomingPane. 
         */
    }

    public DailyTasks (Date date) {
        dayDate = date ;
        innerList = new ListView<>() ;
    }

    public DailyTasks () {
        innerList = new ListView<>() ;
    }

    public void addTask (Task task) {
        innerList.getItems().add(task) ;
    }

    public Date getDayDate() {
        return dayDate;
    }

    public void setDayDate(Date dayDate) {
        this.dayDate = dayDate;
    }

    public ListView<Task> getInnerList() {
        return innerList;
    }

    public void setInnerList(ListView<Task> innerList) {
        this.innerList = innerList;
    }
}
