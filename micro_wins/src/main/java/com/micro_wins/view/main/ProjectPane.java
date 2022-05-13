package com.micro_wins.view.main;

import com.micro_wins.view.FxController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
@FxmlView
public class ProjectPane implements Initializable, FxController {

    @FXML
    private Label completedCnt;

    @FXML
    private Label openTaskCnt;

    @FXML
    private HBox proAddMember;

    @FXML
    private HBox proAddSection;

    @FXML
    private Label workingTaskCnt;

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    @FXML
    void addProMember(KeyEvent event) {

    }

    @FXML
    void addProSection(MouseEvent event) {

    }
}
