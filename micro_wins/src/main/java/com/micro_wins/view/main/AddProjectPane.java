package com.micro_wins.view.main;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import com.micro_wins.constants.Functions;
import com.micro_wins.model.Project;
import com.micro_wins.view.FxController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView
public class AddProjectPane implements Initializable, FxController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker proDeadline;

    @FXML
    private TextArea proDesc;

    @FXML
    private DatePicker proStartDate;

    @FXML
    private TextField proTitle;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancel;

    private Functions functions;

    @FXML
    void addProject(ActionEvent event) {
        String title = proTitle.getText();
        String desc = proDesc.getText();
        LocalDate startLocalDate = proStartDate.getValue();
        Date startDate = functions.localDateToDate(startLocalDate);
        LocalDate deadlineLocal = proDeadline.getValue();
        Date deadline = functions.localDateToDate(deadlineLocal);
        Project newProject = new Project();
        newProject.setProTitle(title);
        newProject.setProDescription(desc);
        newProject.setProStatus(0);
        newProject.setProCompletionPercent(0);
        newProject.setProOwner(11);
        newProject.setProStartDate(startDate);
        newProject.setProDeadline(deadline);
    }

    @FXML
    void cancelAddProject(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
