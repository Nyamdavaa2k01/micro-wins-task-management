package com.micro_wins.view.main;

import com.micro_wins.model.Project;
import com.micro_wins.view.FxController;
import com.micro_wins.holder.ProjectHolder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
@FxmlView
public class ProjectPane implements Initializable, FxController {

    @FXML
    private Text completedTaskCntText;

    @FXML
    private ListView<?> lvCompleted;

    @FXML
    private ListView<?> lvOpen;

    @FXML
    private ListView<?> lvPostponed;

    @FXML
    private ListView<?> lvWorking;

    @FXML
    private Text openTaskCntTxt;

    @FXML
    private Text postponedTaskCntText;

    @FXML
    private HBox proAddMember;

    @FXML
    private HBox proAddSection;

    @FXML
    private Text proDescTxt;

    @FXML
    private Text proTitleTxt;

    @FXML
    private Text workingTaskCntTxt;

    @FXML
    void addProMember(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ProjectHolder projectHolder = ProjectHolder.getInstance();
        Project activeProject = projectHolder.getProject();
        proTitleTxt.setText(activeProject.getProTitle());
        proDescTxt.setText(activeProject.getProDescription());
    }

}
