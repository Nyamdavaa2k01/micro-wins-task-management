package com.micro_wins.view.main;

import com.micro_wins.model.Project;
import com.micro_wins.model.Task;
import com.micro_wins.view.FxController;
import com.micro_wins.holder.ProjectHolder;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
@FxmlView
public class ProjectPane implements Initializable, FxController {

    public List<Task> proTaskList;
    public ObservableList<Task> proTasks;

    @FXML
    private Text completedTaskCntText;

    @FXML
    private ListView<Task> lvCompleted;

    @FXML
    private ListView<Task> lvOpen;

    @FXML
    private ListView<Task> lvPostponed;

    @FXML
    private ListView<Task> lvWorking;

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
