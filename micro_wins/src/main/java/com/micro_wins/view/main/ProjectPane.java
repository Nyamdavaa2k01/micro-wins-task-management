package com.micro_wins.view.main;

import com.micro_wins.view.FxController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
@FxmlView
public class ProjectPane implements Initializable, FxController {
    @FXML
    private HBox pro_add_member;

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    @FXML
    void addMember(MouseEvent event) {

    }
}
