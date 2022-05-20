package com.micro_wins.view.main;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import com.micro_wins.constant.ConstantColors;
import com.micro_wins.constant.Functions;
import com.micro_wins.holder.UserHolder;
import com.micro_wins.model.Project;
import com.micro_wins.model.User;
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

    private User user;

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
        if(projectRepo.findByProTitleAndProOwner(title, UserHolder.getInstance().getUser().getUserId()).size() > 0)
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

            Class<? extends FxController> fxControllerClass = stageManager.getLatestFxControllerClass() ;
            if (fxControllerClass != null) stageManager.rebuildStage(fxControllerClass);
        }
    }

    @FXML
    void cancelAddProject(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stageManager = springAppContext.getBean(StageManager.class);

        proStartDate.setEditable(false);
        proDeadline.setEditable(false);

        UserHolder userHolder = UserHolder.getInstance();
        user = userHolder.getUser();

        /**
         * set instance of ConstantColors
         */
        constantColors = new ConstantColors();

        /**
         * create localDateToDate to convert local date into date from enum of functions
         */
        localDateToDate = Functions.DATE_TO_LOCALDATE;

        /**
         * default values of add project fields
         */
        final LocalDate today = LocalDate.now();
        final LocalDate tomorrow = today.plusDays(1);
        final int defaultStatus = 1;
        final float defaultPercent = 0;

        /**
         * set instance of Project to newProject
         */
        newProject = new Project();

        /**
         * set default value to new project model fields and new project attribute
         */
        newProject.setProStatus(defaultStatus);
        newProject.setProCompletionPercent(defaultPercent);
        newProject.setProOwner(user.getUserId());
        proStartDate.setValue(today);
        proDeadline.setValue(tomorrow);

        /**
         * Save Task Button is disabled while below conditions are happened, and as soon as user start
         * typing task title, button is enabled
         */
        BooleanBinding bindAddProFields = new BooleanBinding() {
            {
                super.bind(proTitle.textProperty());
                super.bind(proDesc.textProperty());
                super.bind(proStartDate.valueProperty());
                super.bind(proDeadline.valueProperty());
            }

            @Override
            protected boolean computeValue() {
                boolean addProBtnDisable = false;

                if(proTitle.getText().isEmpty() || proDesc.getText().isEmpty()){
                    addProBtnDisable = true;
                }

                LocalDate pickedProStartDate = proStartDate.getValue();
                LocalDate pickedProDeadline = proDeadline.getValue();
                LocalDate nowDate = LocalDate.now();

                double compareStartDateNow = pickedProStartDate.compareTo(nowDate);
                double compareDeadlineNow = pickedProDeadline.compareTo(nowDate);
                double compareStartDateProDeadline = pickedProStartDate.compareTo(pickedProDeadline);

                if(compareDeadlineNow < 0 || compareStartDateNow < 0){
                    addProBtnDisable = true;
                }

                if(compareStartDateProDeadline == 0){
                    addProBtnDisable = true;
                }

                return addProBtnDisable;
            }
        };

        btnAddProject.disableProperty().bind(bindAddProFields);
    }

}
