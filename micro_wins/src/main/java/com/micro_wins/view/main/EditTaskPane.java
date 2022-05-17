package com.micro_wins.view.main;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 11/05/2022 - 6:28 PM
 */

import com.micro_wins.view.FxController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
@FxmlView
public class EditTaskPane implements Initializable, FxController {

    @FXML
    private Button cancelBtn;

    @FXML
    private Button reminderBtn;

    @FXML
    private Button saveTaskBtn;

    @FXML
    private Button setPriorityBtn;

    @FXML
    private DatePicker taskDatePicker;

    @FXML
    private TextField taskDescTxt;

    @FXML
    private TextField taskNameTxt;

    @FXML
    void addReminder(ActionEvent event) {

    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    void saveTask(ActionEvent event) {

    }

    @FXML
    void setPriority(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
