package com.micro_wins.view.main;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView
public class ProjectController {
    @FXML
    private HBox pro_add_member;

    @FXML
    void addMember(MouseEvent event) {

    }
}