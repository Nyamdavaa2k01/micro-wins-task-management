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

    @Autowired
    TaskRepo taskRepo ;

    @FXML
    BorderPane borderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // set layout
        borderPane.setTop(stageManager.loadView(HeaderMenuPane.class));
        borderPane.setLeft(stageManager.loadView(NavigationPane.class));

        Task task = new Task("resolve DB", "bolndoo", 3, 1, Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()), 12) ;
        task.setTaskProId(1);
        task.setTaskProTitle("inbox");
        taskRepo.save(task) ;
    }
}