/**
 * @author Nyamka
 * @project micro-wins-task-management
 */

package com.example.micro_wins;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NavigationController {

    private static final String HOME_VIEW = "home-view.fxml";
    private static final String TODAY_VIEW = "home-view.fxml";
    private static final String INBOX_VIEW = "";
    private static final String UPCOMING_VIEW = "";
    private static final String RESULT_VIEW = "";
    private static final String PROJECT_VIEW = "project-view.fxml";
    private Stage stage;

    @Autowired
    private MainController mainController;

    @Autowired
    private ProjectController projectController;

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
        show(TODAY_VIEW, 1366, 400);
        System.out.println("Under development for now :)");
    }

    @FXML
    void navToUpcomingBtn(ActionEvent event) {
        System.out.println("Under development for now :)");
    }

    @FXML
    void seeAllProjects(ActionEvent event) throws IOException {
        show(PROJECT_VIEW, 1366, 400);
        System.out.println("Under development for now :)");
    }

    private void show(String view, double width, double height) throws IOException {
        Scene scene = new Scene(loadFxml(view), width, height);
        stage.setScene(scene);
        stage.show();
    }

    private Parent loadFxml(String view) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
        loader.setControllerFactory(param -> getViewController(view));
        try {
            loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Parent root = loader.getRoot();
        //When add style, use below code
        //root.getStylesheets().add(getClass().getResource(APP_CSS).toExternalForm());
        return root;
    }

    private Object getViewController(String view) {
        if (PROJECT_VIEW.equals(view)) {
            return projectController;
        }
        //else if(YOUR_PAGE_VIEW.equals(view)){ ... }
        return mainController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
