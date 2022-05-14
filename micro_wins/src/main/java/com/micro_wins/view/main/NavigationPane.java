/**
 * @author Nyamka
 * @project micro-wins-task-management
 */

package com.micro_wins.view.main;
import com.micro_wins.view.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@FxmlView
public class NavigationPane {
    private StageManager stageManager;

    private Stage stage;

    @Autowired
    ConfigurableApplicationContext springAppContext;

    @Autowired
    private MainPane mainController;

    @Autowired
    private ProjectPane projectController;

    @Autowired
    private TodayPane todayViewController ;

    //@PostConstruct
    public void initialize() {
        stageManager = springAppContext.getBean(StageManager.class);
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
    private Button seeAllProjectsBtn;

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
    void seeAllProjects(ActionEvent event) throws IOException {
//        stageManager.rebuildStage(ProjectPane.class);
        Stage addProjectStage = new Stage();
        Parent node = stageManager.loadView(AddProjectPane.class);
        addProjectStage.setScene(new Scene(node));
        addProjectStage.initStyle(StageStyle.TRANSPARENT);
        addProjectStage.initModality(Modality.APPLICATION_MODAL);
        addProjectStage.show();
//        StageManager addProjectStageManager = new StageManager(addProjectStage);
        System.out.println("Under development for now :)");
    }

}
