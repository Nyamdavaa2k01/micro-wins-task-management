package com.micro_wins.view.main;

import com.micro_wins.holder.UserHolder;
import com.micro_wins.model.Project;
import com.micro_wins.model.Task;
import com.micro_wins.model.User;
import com.micro_wins.repository.TaskRepo;
import com.micro_wins.view.FxController;
import com.micro_wins.holder.ProjectHolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
@FxmlView
public class ProjectPane implements Initializable, FxController {

    @Autowired
    private TaskRepo taskRepo;

    private List<Task> proTaskList;
    private ObservableList<Task> proTasks;

    private User user;
    private Project activeProject;

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

    /**
     * refresh list view items
     */
    void resetListView(){
        lvOpen.getItems().clear();
        proTaskList = taskRepo.findByTaskUserIdAndTaskProId(11, activeProject.getProId());
        proTasks = FXCollections.observableArrayList(proTaskList);
        proTasks.forEach(project -> {
            lvOpen.getItems().add(project);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserHolder userHolder = UserHolder.getInstance();
        ProjectHolder projectHolder = ProjectHolder.getInstance();
        user = userHolder.getUser();
        activeProject = projectHolder.getProject();

        resetListView();

        lvOpen.setCellFactory(param -> new ListCell<Task>() {
                    @Override
                    protected void updateItem(Task item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(null);
                        setGraphic(null);

                        if (!empty && item != null) {
                            //...
                            
                        }
                    }
                });

        proTitleTxt.setText(activeProject.getProTitle());
        proDescTxt.setText(activeProject.getProDescription());
    }

}
