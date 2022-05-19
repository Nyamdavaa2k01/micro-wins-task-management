package com.micro_wins.view.main;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 11/05/2022 - 6:28 PM
 * @purpose
 * @definition
 */

import com.micro_wins.constant.ConstantStyles;
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
import com.micro_wins.view.StageManager;
import com.sun.javafx.reflect.FieldUtil;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Controller
@FxmlView
public class EditTaskPane implements Initializable, FxController {

    @Autowired
    DictTypeRepo dictTypeRepo;

    @Autowired
    DictRepo dictRepo;

    private Task activeTask;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    ConfigurableApplicationContext springAppContext;

    private Functions dateToLocalDate;

    private Functions localDateToDate;

    private Stage priorityButtonsStage;

    private List<Dict> priorityDictionary, statusDictionary;

    private Functions informationAlert;

    private CustomPopup customPopup;

    private ObservableList<Dict> statusList;

    private ConstantStyles constantStyles;

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
    private ComboBox<Dict> taskStatusCbx;


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
        String taskName = taskNameTxt.getText();
        String taskDesc = taskDescTxt.getText();
        Date taskStartDate = localDateToDate.localDateToDate(taskDatePicker.getValue());
        activeTask.setTaskTitle(taskName);
        activeTask.setTaskDefinition(taskDesc);
        activeTask.setTaskStartDate(taskStartDate);
        activeTask.setTaskStatus(taskStatusCbx.getSelectionModel().getSelectedItem().getDictId());
        Task savedTask = taskRepo.save(activeTask);
        if(savedTask != null){

            saveTaskBtn.disableProperty().unbind();

            Stage stage = (Stage) saveTaskBtn.getScene().getWindow();
            stage.close();

            /**
             * Go to the latest scene
             */
            Class<? extends FxController> fxControllerClass = stageManager.getLatestFxControllerClass() ;
            if (fxControllerClass != null) stageManager.rebuildStage(fxControllerClass);
        }
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
        stageManager = springAppContext.getBean(StageManager.class);

        TaskHolder taskHolder = TaskHolder.getInstance();
        activeTask = taskHolder.getTask();
        priorityDictionary = getDictByDictType("priority");
        statusDictionary = getDictByDictType("status");
        constantStyles = new ConstantStyles();

        statusList = FXCollections.observableArrayList(statusDictionary);
        taskStatusCbx.setItems(statusList);
        taskStatusCbx.getSelectionModel().select(0);

        Callback<ListView<Dict>, ListCell<Dict>> factory = lv -> new ListCell<Dict>() {

            @Override
            protected void updateItem(Dict item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getDictName());
            }

        };

        taskStatusCbx.setCellFactory(factory);
        taskStatusCbx.setButtonCell(factory.call(null));

        dateToLocalDate = Functions.DATE_TO_LOCALDATE;
        localDateToDate = Functions.LOCALDATE_TO_DATE;
        informationAlert = Functions.INFORMATION_ALERT;
        customPopup = CustomPopup.POPUP;

        taskNameTxt.setText(activeTask.getTaskTitle());
        taskDescTxt.setText(activeTask.getTaskDefinition());
        taskDatePicker.setValue(dateToLocalDate.dateToLocalDate(activeTask.getTaskStartDate()));

        Button priBtn = new Button() ;
        priBtn.setAlignment(Pos.TOP_LEFT);
        priBtn.setStyle(constantStyles.getDEFAULT_BUTTON_STYLE());
        priBtn.setOnAction(e -> {
            setPriority(e);
        });

        Optional<Dict> activeDict = priorityDictionary.stream().filter(priDict -> priDict.getDictId() == activeTask.getTaskPriority()).findFirst();

        String buttonText = activeDict.get().getDictName();
        String buttonGraphicFilePath ="file:micro_wins/src/main/resources/images/priority-"+(activeDict.get().getDictValue())+"-icon.png" ;
        ImageView buttonGraphicImage = new ImageView(new Image(buttonGraphicFilePath)) ;
        buttonGraphicImage.setFitHeight(25);
        buttonGraphicImage.setFitWidth(25);
        priBtn.setText(buttonText);
        priBtn.setGraphic(buttonGraphicImage);
        priBtn.setPrefWidth(200);
        priBtn.setPrefHeight(43);

        setPriorityBtn.setGraphic(priBtn.getGraphic());

        List<Dict> filteredStatus = statusDictionary.stream().filter(sDic -> (sDic.getDictId() == activeTask.getTaskStatus())).collect(Collectors.toList());

        if(filteredStatus.size() == 0){
            taskStatusCbx.setValue(new Dict());
        }else{
            taskStatusCbx.getSelectionModel().select(filteredStatus.get(0));
        }

        /**
         * Save Task Button is disabled while Task Title TextField and Task Description TextField are empty, and as soon as user start
         * typing task title, button is enabled
         */
        BooleanBinding bindProTitleAndDesc = new BooleanBinding() {
            {
                super.bind(taskNameTxt.textProperty());
                super.bind(taskDescTxt.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return (taskNameTxt.getText().isEmpty() || taskDescTxt.getText().isEmpty());
            }
        };

        saveTaskBtn.disableProperty().bind(bindProTitleAndDesc);
    }

    List<Dict> getDictByDictType(String dictTypeTxt){
        DictType dictType = dictTypeRepo.findByDtTypeName(dictTypeTxt);
        List<Dict> dictList = dictRepo.findDictByDictTypeNo(dictType.getDtTypeNo());
        return dictList;
    }
}
