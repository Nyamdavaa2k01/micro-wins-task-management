/**
 * @author Nyamka
 * @project micro-wins-task-management
 */

package com.micro_wins.view.main;
import com.micro_wins.model.Project;
import com.micro_wins.repository.ProjectRepo;
import com.micro_wins.view.StageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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

@Controller
@FxmlView
public class NavigationPane {
    private StageManager stageManager;

    private Stage stage;

    private List<Project> projectList;
    private ObservableList<Project> projects;

    @Autowired
    ProjectRepo projectRepo;

    @Autowired
    ConfigurableApplicationContext springAppContext;

    @Autowired
    private MainPane mainController;

    @Autowired
    private ProjectPane projectController;

    @Autowired
    private TodayPane todayViewController ;

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
    private ListView<Project> lvProjects;

    void resetListView(){
        lvProjects.getItems().clear();
        projectList = projectRepo.findProjectsByProOwner(11);
        projects = FXCollections.observableArrayList(projectList);
        lvProjects.getItems().addAll(projects);
    }

    //@PostConstruct
    public void initialize() {
        stageManager = springAppContext.getBean(StageManager.class);

        /**
         *
         */
        resetListView();
        lvProjects.setCellFactory(param -> new ListCell<Project>() {
            @Override
            protected void updateItem(Project item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);

                if (!empty && item != null){
                    Button btn = new Button(item.getProTitle());
                    btn.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
//                            stageManager.rebuildStage(ProjectPane.class);
                        }
                    });

                    final ContextMenu contextMenu = new ContextMenu();
                    MenuItem delete = new MenuItem("Delete");
                    delete.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("deleted");
                            projectRepo.delete(item);
                            stageManager.rebuildStage(TodayPane.class);
                        }
                    });
                    contextMenu.getItems().add(delete);

                    btn.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if(mouseEvent.getButton() == MouseButton.SECONDARY){
                                System.out.println("mouse right clicked");
                                btn.setContextMenu(contextMenu);
                            }
                        }
                    });
                    setGraphic(btn);
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
        System.out.println("Under development for now :)");
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
        System.out.println("Under development for now :)");
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
