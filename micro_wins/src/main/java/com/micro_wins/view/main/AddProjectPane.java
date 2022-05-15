package com.micro_wins.view.main;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.micro_wins.constants.ConstantColors;
import com.micro_wins.constants.Functions;
import com.micro_wins.model.Project;
import com.micro_wins.repository.ProjectRepo;
import com.micro_wins.view.FxController;
import com.micro_wins.view.StageManager;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView
public class AddProjectPane implements Initializable, FxController {

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    ConfigurableApplicationContext springAppContext;

    @Autowired
    ProjectRepo projectRepo;

    private Functions localDateToDate;
    private Project newProject;

    private ConstantColors constantColors;

    @FXML
    private DatePicker proDeadline;

    @FXML
    private TextArea proDesc;

    @FXML
    private DatePicker proStartDate;

    @FXML
    private TextField proTitle;

    @FXML
    private Button btnAddProject;

    @FXML
    private Button btnCancel;

    @FXML
    void addProject(ActionEvent event) {

        String title = proTitle.getText();
        String desc = proDesc.getText();

        /**
         * whether check be same title project of saved projects
         */
        if(projectRepo.findByProTitleAndProOwner(title, 11).size() > 0)
        {
            proTitle.setStyle("-fx-background-color: " + constantColors.getWARNING_COLOR() + ";");
            return;
        }else{
            proTitle.setStyle("-fx-background-color: " + constantColors.getWHITE() + ";");
        }
        /**
         * set values to new instant of Project
         */
        LocalDate startLocalDate = proStartDate.getValue();
        Date startDate = localDateToDate.localDateToDate(startLocalDate);
        LocalDate deadlineLocal = proDeadline.getValue();
        Date deadline = localDateToDate.localDateToDate(deadlineLocal);
        newProject.setProTitle(title);
        newProject.setProDescription(desc);
        newProject.setProStartDate(startDate);
        newProject.setProDeadline(deadline);

        /**
         * insert new project into mw_project table
         */
        Project savedProject = projectRepo.save(newProject);

        /**
         * when save successfully, close stage to add new project and unbind button to and project
         */
        if(savedProject != null){
            btnAddProject.disableProperty().unbind();

            Stage stage = (Stage) btnAddProject.getScene().getWindow();
            stage.close();

            NavigationPane navigationPane = NavigationPane.getInstance();
            stageManager.loadView(navigationPane.getClass());
            navigationPane.lvProjects = new ListView<>();
            navigationPane.projectRepo = projectRepo;
            navigationPane.resetListView();
        }
    }

    @FXML
    void cancelAddProject(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stageManager = springAppContext.getBean(StageManager.class);

        /**
         * set instance of ConstantColors
         */
        constantColors = new ConstantColors();

        /**
         * create localDateToDate to convert local date into date from enum of functions
         */
        localDateToDate = Functions.DATE_TO_LOCALDATE;

        /**
         * set instance of Project to newProject
         */
        newProject = new Project();

        /**
         * set default value to new project model fields and new project attribute
         */
        newProject.setProStatus(1);
        newProject.setProCompletionPercent(0);
        newProject.setProOwner(11);
        LocalDate today = LocalDate.now();
        proStartDate.setValue(today);
        LocalDate tomorrow = today.plusDays(1);
        proDeadline.setValue(tomorrow);

        /**
         * Add Task Button is disabled while Project Title TextField and Project Description TextField are empty, and as soon as user start
         * typing task title, button is enabled
         */
        BooleanBinding bindProTitleAndDesc = new BooleanBinding() {
            {
                super.bind(proTitle.textProperty());
                super.bind(proDesc.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return (proTitle.getText().isEmpty() || proDesc.getText().isEmpty());
            }
        };

        btnAddProject.disableProperty().bind(bindProTitleAndDesc);
    }

}
