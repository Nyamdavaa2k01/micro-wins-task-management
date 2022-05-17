package com.micro_wins.view.main;

import com.micro_wins.constant.Functions;
import com.micro_wins.holder.UserHolder;
import com.micro_wins.model.Project;
import com.micro_wins.model.Task;
import com.micro_wins.model.User;
import com.micro_wins.repository.TaskRepo;
import com.micro_wins.view.FxController;
import com.micro_wins.holder.ProjectHolder;
import com.micro_wins.view.StageManager;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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

    private Functions dateToString;

    private final double LIST_CELL_HEIGHT = 70;

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
        proTaskList = taskRepo.findByTaskUserIdAndTaskProId(12, activeProject.getProId());
        proTasks = FXCollections.observableArrayList(proTaskList);
        System.out.println("pro tasks: " + proTaskList.size());
        proTasks.forEach(proTask -> {
            lvOpen.getItems().add(proTask);
        });
        lvOpen.prefHeightProperty().bind(Bindings.size(proTasks).multiply(LIST_CELL_HEIGHT));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stageManager = springAppContext.getBean(StageManager.class);
        UserHolder userHolder = UserHolder.getInstance();
        ProjectHolder projectHolder = ProjectHolder.getInstance();
        user = userHolder.getUser();
        activeProject = projectHolder.getProject();
        dateToString = Functions.DATE_TO_LOCALDATE;

        resetListView();

        lvOpen.setCellFactory(param -> {
            return new ListCell<Task>() {
                @Override
                protected void updateItem(Task item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(null);
                    setGraphic(null);

                    if (!empty && item != null) {
                        VBox taskCard = new VBox();
                        taskCard.setStyle("-fx-background-color: #fff; -fx-border-color: #000; -fx-border-width: 1px; -fx-border-radius: 30px; -fx-background-radius: 30px;");
                        taskCard.setPadding(new Insets(5));
                        taskCard.setPrefHeight(60);
                        taskCard.setSpacing(5);

                        HBox top = new HBox();
                        top.setAlignment(Pos.CENTER);
                        top.setSpacing(10);
                        Circle statusCircle = new Circle(8);
                        statusCircle.setFill(Color.rgb(255, 0, 0, 0.4));
                        statusCircle.setStrokeWidth(3);
                        statusCircle.setStroke(Color.RED);

                        Label taskNameLbl = new Label(item.getTaskTitle());
                        taskNameLbl.setWrapText(true);
                        taskNameLbl.setPrefWidth(80);
                        taskNameLbl.setTextFill(Color.BLACK);

                        Image taskUserImg = new Image("file:micro_wins/src/main/resources/images/clarity_assign_user_line.png");
                        ImageView taskUserImgView = new ImageView(taskUserImg);
                        taskUserImgView.setFitHeight(20);
                        taskUserImgView.setFitWidth(20);

                        top.getChildren().addAll(statusCircle, taskNameLbl, taskUserImgView);

                        HBox bottom = new HBox();
                        bottom.setAlignment(Pos.CENTER);
                        bottom.setSpacing(5);
                        Label taskStartDate = new Label(dateToString.dateToLocalDate(item.getTaskStartDate()).toString());
                        taskStartDate.setTextFill(Color.BLACK);
                        taskStartDate.setPadding(new Insets(0, 2, 0, 2));
                        taskStartDate.setStyle("-fx-background-color: #fff; -fx-border-color: #000; -fx-border-width: 1px; -fx-border-radius: 30px; -fx-background-radius: 30px;");
                        Image arrowRight = new Image("file:micro_wins/src/main/resources/images/ci_sub_right.png");
                        ImageView arrowRightView = new ImageView(arrowRight);
                        arrowRightView.setFitHeight(15);
                        arrowRightView.setFitWidth(30);
                        bottom.getChildren().addAll(arrowRightView, taskStartDate);

                        taskCard.getChildren().addAll(top, bottom);
                        setGraphic(taskCard);
                    }
                }
            };
        });

        lvOpen.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println("clicked on " + lvOpen.getSelectionModel().getSelectedItem());
                Stage editTaskStage = new Stage();
                Parent node = stageManager.loadView(EditTaskPane.class);
                Scene scene = new Scene(node);
                scene.setFill(Color.TRANSPARENT);
                editTaskStage.setScene(scene);
                editTaskStage.initStyle(StageStyle.TRANSPARENT);
                editTaskStage.initModality(Modality.APPLICATION_MODAL);
                editTaskStage.show();
            }
        });

        proTitleTxt.setText(activeProject.getProTitle());
        proDescTxt.setText(activeProject.getProDescription());
    }

}
