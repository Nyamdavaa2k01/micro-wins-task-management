/**
 * @author Nyamka
 * @project micro-wins-task-management
 */

package com.micro_wins.view.main;

import com.micro_wins.model.Task;
import com.micro_wins.repository.TaskRepo;
import com.micro_wins.view.FxController;
import com.micro_wins.view.StageManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
// somehow cant use javafx.awt.*

@Controller
@FxmlView
public class MainPane implements Initializable, FxController {

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    public BorderPane borderPane;

    private final static MainPane INSTANCE = new MainPane() ;

    private MainPane(){}

    /**
     * get global static instance of NavigationPane class
     * @return
     */
    public static MainPane getInstance() {
        return INSTANCE;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // set layout
        borderPane.setTop(stageManager.loadView(HeaderMenuPane.class));
        borderPane.setLeft(stageManager.loadView(NavigationPane.class));
        borderPane.setCenter(stageManager.loadView(TodayPane.class));
    }
}