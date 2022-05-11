/**
 * @author Nyamka
 * @project micro-wins-task-management
 */

package com.example.micro_wins.controller;

import com.example.micro_wins.view.FxController;
import com.example.micro_wins.view.StageManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;
// somehow cant use javafx.awt.*

@Controller
@FxmlView
public class MainController implements Initializable, FxController{

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    BorderPane borderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // set layout
        borderPane.setTop(stageManager.loadView(HeaderMenuController.class));
        borderPane.setLeft(stageManager.loadView(NavigationController.class));
    }

}