/**
 * @author Nyamka
 * @project micro-wins-task-management
 */

package com.micro_wins.view.main;
import com.micro_wins.constant.Functions;
import com.micro_wins.holder.UserHolder;
import com.micro_wins.model.Project;
import com.micro_wins.model.User;
import com.micro_wins.repository.ProjectRepo;
import com.micro_wins.view.StageManager;
import com.micro_wins.holder.ProjectHolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@FxmlView
public class NavigationPane {
    private StageManager stageManager;

    private Stage stage;

    private User user;

    public List<Project> projectList;
    public ObservableList<Project> projects;

    private Functions deleteConfirmAlert;

    @Autowired
    public ProjectRepo projectRepo;

    @Autowired
    ConfigurableApplicationContext springAppContext;

    @Autowired
    private MainPane mainController;

    @Autowired
    private ProjectPane projectController;

    @Autowired
    private UpcomingPane upcomingController ;

    @FXML
    private VBox navBar;

    @FXML
    private Button navToCustomizeBtn;

    @FXML
    private Button navToInboxBtn;

    @FXML
    private Button navToResultsBtn;

    @FXML
    private Button navToTodayBtn;

    @FXML
    private Button navToUpcomingBtn;

    @FXML
    private VBox projectButtons;

    @FXML
    private Button addNewProjectBtn;

    @FXML
    public ListView<Project> lvProjects;

    /**
     * refresh list view items
     */
    void resetListView(){
        lvProjects.getItems().clear();
        projectList = projectRepo.findProjectsByProOwner(11);
        projects = FXCollections.observableArrayList(projectList.stream().filter(project -> !project.getProTitle().toLowerCase().equals("inbox")).collect(Collectors.toList()));
        projects.forEach(project -> {
            lvProjects.getItems().add(project);
        });
    }

    /**
     *
     */
    public void initialize() {
        stageManager = springAppContext.getBean(StageManager.class);
        UserHolder userHolder = UserHolder.getInstance();
        user = userHolder.getUser();
        deleteConfirmAlert = Functions.DELETE_CONFIRM_ALERT;

        resetListView();

        lvProjects.setCellFactory(param -> new ListCell<Project>() {
            @Override
            protected void updateItem(Project item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);
                setGraphic(null);

                if (!empty && item != null){
                    HBox hBox = new HBox(5);
                    hBox.setAlignment(Pos.CENTER);

                    Button btnTitle = new Button(item.getProTitle());
                    btnTitle.setStyle("-fx-pref-width:180;\n" +
                            "-fx-pref-height:35;\n" +
                            "-fx-background-radius : 25;\n" +
                            "-fx-text-fill : white;\n" +
                            "-fx-font-size: 18;\n" +
                            "-fx-graphic-text-gap: 30;\n" +
                            "-fx-alignment: center-left;\n" +
                            "-fx-padding: 0 5 0 15;\n" +
                            "\n" +
                            "    -fx-background-color: #4CAF50;");
                    btnTitle.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            ProjectHolder projectHolder = ProjectHolder.getInstance();
                            projectHolder.setProject(item);
                            stageManager.rebuildStage(ProjectPane.class);
                        }
                    });

                    Image deleteIcon = new Image("file:micro_wins/src/main/resources/images/white-x.png");
                    ImageView deleteIconImgView = new ImageView(deleteIcon);
                    deleteIconImgView.setFitHeight(10);
                    deleteIconImgView.setFitWidth(10);

                    Button btnDelete = new Button("", deleteIconImgView);
                    btnDelete.setContentDisplay(ContentDisplay.CENTER);
                    btnDelete.setStyle("-fx-pref-width:10;\n" +
                            "-fx-pref-height: 10;\n" +
                            "-fx-background-radius : 25;\n" +
                            "-fx-text-fill : white;\n" +
                            "-fx-font-size: 10;\n" +
                            "-fx-graphic-text-gap: 0;\n" +
                            "-fx-alignment: center;\n" +
                            "-fx-padding: 3;\n" +
                            "\n" +
                            "-fx-background-color: transparent;");

                    btnDelete.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            btnDelete.setStyle("-fx-pref-width:10;\n" +
                                    "-fx-pref-height: 10;\n" +
                                    "-fx-background-radius : 25;\n" +
                                    "-fx-text-fill : white;\n" +
                                    "-fx-font-size: 10;\n" +
                                    "-fx-graphic-text-gap: 0;\n" +
                                    "-fx-alignment: center;\n" +
                                    "-fx-padding: 3;\n" +
                                    "\n" +
                                    "-fx-background-color: rgba(255, 0, 0, 1);");
                            event.consume();
                        }
                    });

                    btnDelete.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            btnDelete.setStyle("-fx-pref-width:10;\n" +
                                    "-fx-pref-height: 10;\n" +
                                    "-fx-background-radius : 25;\n" +
                                    "-fx-text-fill : white;\n" +
                                    "-fx-font-size: 10;\n" +
                                    "-fx-graphic-text-gap: 0;\n" +
                                    "-fx-alignment: center;\n" +
                                    "-fx-padding: 3;\n" +
                                    "\n" +
                                    "-fx-background-color: transparent;");
                            event.consume();
                        }
                    });

                    btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if(deleteConfirmAlert.deleteConfirmAlert("Confirm Delete", "Are you sure you want to delete this project?", "Yes", "No")){
                                projectRepo.delete(item);
                                resetListView();

                                Scene scene = btnDelete.getScene();
                                if(scene == null){
                                    return;
                                }
                                Stage stage = (Stage) scene.getWindow();
                                if(stage.isShowing()){
                                    stageManager.rebuildStage(TodayPane.class);
                                }
                            }
                        }
                    });

                    hBox.getChildren().addAll(btnTitle, btnDelete);
                    setGraphic(hBox);
                }
            }
        });

        lvProjects.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        if (stage == null)
            stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(true);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds() ;
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
    }

    @FXML
    void navToCustomize(ActionEvent event) {
        System.out.println("Under development for now :)");
    }

    @FXML
    void navToInbox(ActionEvent event) {
        stageManager.rebuildStage(InboxPane.class);
    }

    @FXML
    void navToResults(ActionEvent event) {
        System.out.println("Under development for now :)");
    }

    @FXML
    void navToToday(ActionEvent event) throws IOException {
        stageManager.rebuildStage(TodayPane.class);
    }

    @FXML
    void navToUpcoming(ActionEvent event) {
        stageManager.rebuildStage(UpcomingPane.class);
    }

    @FXML
    void addNewProject(ActionEvent event){
        Stage addProjectStage = new Stage();
        Parent node = stageManager.loadView(AddProjectPane.class);
        Scene scene = new Scene(node);
        scene.setFill(Color.TRANSPARENT);
        addProjectStage.setScene(scene);
        addProjectStage.initStyle(StageStyle.TRANSPARENT);
        addProjectStage.initModality(Modality.APPLICATION_MODAL);
        addProjectStage.show();
    }
}
