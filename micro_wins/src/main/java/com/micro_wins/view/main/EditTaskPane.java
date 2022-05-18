package com.micro_wins.view.main;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 11/05/2022 - 6:28 PM
 */

import com.micro_wins.constant.Functions;
import com.micro_wins.holder.TaskHolder;
import com.micro_wins.modal.CustomPopup;
import com.micro_wins.model.Dict;
import com.micro_wins.model.DictType;
import com.micro_wins.model.Task;
import com.micro_wins.repository.DictRepo;
import com.micro_wins.repository.DictTypeRepo;
import com.micro_wins.repository.TaskRepo;
import com.micro_wins.view.FxController;
import com.sun.javafx.reflect.FieldUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
@FxmlView
public class EditTaskPane implements Initializable, FxController {

    @Autowired
    DictTypeRepo dictTypeRepo;

    @Autowired
    DictRepo dictRepo;

    private Task activeTask;

    private Functions dateToLocalDate;

    private Stage priorityButtonsStage;

    private List<Dict> priorityDictionary, statusDictionary;

    private Functions informationAlert;

    private CustomPopup customPopup;

    @Autowired
    private TaskRepo taskRepo;

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
        informationAlert.informationAlert("Information", "Sorry, The development of this section is not complete.", "OK");
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

        if (priorityButtonsStage != null) priorityButtonsStage.close();
        priorityButtonsStage = new Stage( );
        priorityButtonsStage.initStyle(StageStyle.UNDECORATED);

        /**
         * Changing the initial position of priorityButtonsStage relative to setPriorityBtn
         */
        Bounds setPriorityBtnBounds = setPriorityBtn.localToScreen(setPriorityBtn.getBoundsInLocal()) ;
        int priorityButtonsStageInitPosX = (int) setPriorityBtnBounds.getMinX();
        int priorityButtonsStageInitPosY = (int) setPriorityBtnBounds.getMaxY() ;
        priorityButtonsStage.setX(priorityButtonsStageInitPosX);
        priorityButtonsStage.setY(priorityButtonsStageInitPosY);

        customPopup.callSetPriorityPopup(priorityButtonsStageInitPosX, priorityButtonsStageInitPosY, priorityDictionary, setPriorityBtn, activeTask);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TaskHolder taskHolder = TaskHolder.getInstance();
        activeTask = taskHolder.getTask();
        priorityDictionary = getDictByDictType("priority");

        dateToLocalDate = Functions.DATE_TO_LOCALDATE;
        informationAlert = Functions.INFORMATION_ALERT;
        customPopup = CustomPopup.POPUP;

        taskNameTxt.setText(activeTask.getTaskTitle());
        taskDescTxt.setText(activeTask.getTaskDefinition());
        taskDatePicker.setValue(dateToLocalDate.dateToLocalDate(activeTask.getTaskStartDate()));
    }

    List<Dict> getDictByDictType(String dictTypeTxt){
        DictType dictType = dictTypeRepo.findByDtTypeName(dictTypeTxt);
        List<Dict> dictList = dictRepo.findDictByDictTypeNo(dictType.getDtTypeNo());
        return dictList;
    }
}
