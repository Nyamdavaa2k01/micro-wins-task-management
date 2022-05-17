package com.micro_wins.view.main;

import com.micro_wins.holder.UserHolder;
import com.micro_wins.model.Project;
import com.micro_wins.model.Task;
import com.micro_wins.model.User;
import com.micro_wins.repository.TaskRepo;
import com.micro_wins.view.FxController;
import com.micro_wins.holder.ProjectHolder;
import com.micro_wins.view.StageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
@FxmlView
public class ProjectPane implements Initializable, FxController {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    ConfigurableApplicationContext springAppContext;

    private StageManager stageManager;
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
    private Button addTaskToProjectBtn;

    @FXML
    void addProMember(MouseEvent event) {

    }

    @FXML
    void addTaskToProject(ActionEvent event) {
        stageManager = springAppContext.getBean(StageManager.class);
        stageManager.addStage(AddTaskPane.class);
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
                            VBox taskCard = new VBox();

                            HBox top = new HBox();
                            Circle statusCircle = new Circle(5);
                            statusCircle.setStyle("-fx-background-color: #ff000040;");
                            statusCircle.setStrokeWidth(2);
                            statusCircle.setStroke(Color.RED);

                            Label taskNameLbl = new Label("task");

                            Image taskUserImg = new Image("file:micro_wins/src/main/resources/images/clarity_assign_user_line.png");
                            ImageView taskUserImgView = new ImageView(taskUserImg);
                            taskUserImgView.setFitHeight(20);
                            taskUserImgView.setFitWidth(20);

                            top.getChildren().addAll(statusCircle, taskNameLbl, taskUserImgView);

                            HBox center = new HBox();

                            HBox bottom = new HBox();

                            taskCard.getChildren().addAll(top, center, bottom);
                        }
                    }
                });

        proTitleTxt.setText(activeProject.getProTitle());
        proDescTxt.setText(activeProject.getProDescription());
    }

}
