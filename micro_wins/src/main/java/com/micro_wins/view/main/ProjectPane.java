package com.micro_wins.view.main;


/**
 * @author Bagabandi
 * @project micro-wins-task-management
 * @definition ProjectPane class allows user to handle the project one created.
 */

import com.micro_wins.constant.ConstantColors;
import com.micro_wins.constant.Functions;
import com.micro_wins.holder.TaskHolder;
import com.micro_wins.holder.UserHolder;
import com.micro_wins.modal.ProjectTaskCard;
import com.micro_wins.model.*;
import com.micro_wins.repository.DictRepo;
import com.micro_wins.repository.DictTypeRepo;
import com.micro_wins.repository.TaskRepo;
import com.micro_wins.utils.DoughnutChart;
import com.micro_wins.view.FxController;
import com.micro_wins.holder.ProjectHolder;
import com.micro_wins.view.StageManager;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Controller
@FxmlView
public class ProjectPane implements Initializable, FxController {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    ConfigurableApplicationContext springAppContext;

    @Autowired
    DictRepo dictRepo;

    @Autowired
    DictTypeRepo dictTypeRepo;

    private StageManager stageManager;
    private List<Task> proTaskList;
    private ObservableList<Task> proTasks;

    private User user;
    private Project activeProject;

    private Functions dateToString;
    private ProjectTaskCard projectTaskCard;

    private final double LIST_CELL_HEIGHT = 70;

    private List<Dict> statusDictionary;

    private TaskHolder taskHolder;

    private ConstantColors constantColors;

    @FXML
    private Text completedTaskCntTxt;

    @FXML
    private ListView<Task> lvCompleted;

    @FXML
    private ListView<Task> lvOpen;

    @FXML
    private ListView<Task> lvPostponed;

    @FXML
    private ListView<Task> lvWorking;

    @FXML
    private Text openTaskCntTxt;

    @FXML
    private Text postponedTaskCntTxt;

    @FXML
    private HBox proAddMember;

    @FXML
    private HBox proAddSection;

    @FXML
    private Text proDescTxt;

    @FXML
    private Text proTitleTxt;

    @FXML
    private Text workingTaskCntTxt;

    @FXML
    private Button addTaskToProjectBtn;

    @FXML
    private DoughnutChart doughnutChart;

    @FXML
    void addProMember(MouseEvent event) {

    }

    @FXML
    void addTaskToProject(ActionEvent event) {
        stageManager = springAppContext.getBean(StageManager.class);
        stageManager.addStage(AddTaskPane.class);
    }

    /**
     * When elements in the listview (named lvOpen), remove all previous elements and
     * reload updated elements from the DB to show the latest update to the user.
     */
    void resetListView(){
        // list view - үүдйиг цэвэрлэнэ.
        lvOpen.getItems().clear();
        lvWorking.getItems().clear();
        lvPostponed.getItems().clear();
        lvCompleted.getItems().clear();

        //Өгөгдлийн сангаас тухайн нэвтэрсэн хэрэглэгчийн id болон уг хэрэглэгчийн үүсгэсэн project id - д харгалзах даалгавруудыг авчирна.
        proTaskList = taskRepo.findByTaskUserIdAndTaskProId(user.getUserId(), activeProject.getProId());
        proTasks = FXCollections.observableArrayList(proTaskList);

        //Авчирсан даалгавруудыг төлвөөр нь ангилан харгалзах list view бүрт нэмнэ.
        proTasks.forEach(proTask -> {
            Optional<Dict> staDict = dictRepo.findById(proTask.getTaskStatus());
            if("completed".equals(staDict.get().getDictName())){
                lvCompleted.getItems().add(proTask);
            }else if("postponed".equals(staDict.get().getDictName())){
                lvPostponed.getItems().add(proTask);
            }else if("working".equals(staDict.get().getDictName())){
                lvWorking.getItems().add(proTask);
            }else{
                lvOpen.getItems().add(proTask);
            }
        });

        //Агуулж байгаа даалгаврын тооноос хамаарч list view - ийн өндөр даган хувьсана.
        lvOpen.prefHeightProperty().bind(Bindings.size(lvOpen.getItems()).multiply(LIST_CELL_HEIGHT));
        lvWorking.prefHeightProperty().bind(Bindings.size(lvWorking.getItems()).multiply(LIST_CELL_HEIGHT));
        lvPostponed.prefHeightProperty().bind(Bindings.size(lvPostponed.getItems()).multiply(LIST_CELL_HEIGHT));
        lvCompleted.prefHeightProperty().bind(Bindings.size(lvCompleted.getItems()).multiply(LIST_CELL_HEIGHT));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stageManager = springAppContext.getBean(StageManager.class);

        constantColors = new ConstantColors();

        //Программд нэвтэрсэн хэрэглэгчийн олж авна
        UserHolder userHolder = UserHolder.getInstance();
        ProjectHolder projectHolder = ProjectHolder.getInstance();
        user = userHolder.getUser();

        activeProject = projectHolder.getProject();
        dateToString = Functions.DATE_TO_LOCALDATE;
        projectTaskCard = ProjectTaskCard.PRO_TASK_CARD;
        statusDictionary = getDictByDictType("status");

        taskHolder = TaskHolder.getInstance();

        resetListView();

        lvOpen.setCellFactory(param -> {
            return new ListCell<Task>() {
                @Override
                protected void updateItem(Task item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(null);
                    setGraphic(null);

                    if (!empty && item != null) {
                        VBox taskCard = projectTaskCard.getProOpenTaskCard(item);
                        setGraphic(taskCard);
                    }
                }
            };
        });

        lvWorking.setCellFactory(param -> {
            return new ListCell<Task>() {
                @Override
                protected void updateItem(Task item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(null);
                    setGraphic(null);

                    if (!empty && item != null) {
                        VBox taskCard = projectTaskCard.getProWorkingTaskCard(item);
                        setGraphic(taskCard);
                    }
                }
            };
        });

        lvPostponed.setCellFactory(param -> {
            return new ListCell<Task>() {
                @Override
                protected void updateItem(Task item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(null);
                    setGraphic(null);

                    if (!empty && item != null) {
                        VBox taskCard = projectTaskCard.getProPostponedTaskCard(item);
                        setGraphic(taskCard);
                    }
                }
            };
        });

        lvCompleted.setCellFactory(param -> {
            return new ListCell<Task>() {
                @Override
                protected void updateItem(Task item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(null);
                    setGraphic(null);

                    if (!empty && item != null) {
                        VBox taskCard = projectTaskCard.getProCompletedTaskCard(item);
                        setGraphic(taskCard);
                    }
                }
            };
        });

        lvOpen.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                Task lvOpenItem = lvOpen.getSelectionModel().getSelectedItem();
                taskHolder.setTask(lvOpenItem);
                Stage editTaskStage = new Stage();
                Parent node = stageManager.loadView(EditTaskPane.class);
                Scene scene = new Scene(node);
                scene.setFill(Color.TRANSPARENT);
                editTaskStage.setScene(scene);
                editTaskStage.initStyle(StageStyle.TRANSPARENT);
                editTaskStage.initModality(Modality.APPLICATION_MODAL);
                editTaskStage.show();
            }
        });

        lvWorking.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                Task lvWorkingItem = lvWorking.getSelectionModel().getSelectedItem();
                taskHolder.setTask(lvWorkingItem);
                Stage editTaskStage = new Stage();
                Parent node = stageManager.loadView(EditTaskPane.class);
                Scene scene = new Scene(node);
                scene.setFill(Color.TRANSPARENT);
                editTaskStage.setScene(scene);
                editTaskStage.initStyle(StageStyle.TRANSPARENT);
                editTaskStage.initModality(Modality.APPLICATION_MODAL);
                editTaskStage.show();
            }
        });

        lvPostponed.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                Task lvPostponedItem = lvPostponed.getSelectionModel().getSelectedItem();
                taskHolder.setTask(lvPostponedItem);
                Stage editTaskStage = new Stage();
                Parent node = stageManager.loadView(EditTaskPane.class);
                Scene scene = new Scene(node);
                scene.setFill(Color.TRANSPARENT);
                editTaskStage.setScene(scene);
                editTaskStage.initStyle(StageStyle.TRANSPARENT);
                editTaskStage.initModality(Modality.APPLICATION_MODAL);
                editTaskStage.show();
            }
        });

        lvCompleted.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                Task lvCompletedItem = lvCompleted.getSelectionModel().getSelectedItem();
                taskHolder.setTask(lvCompletedItem);
                Stage editTaskStage = new Stage();
                Parent node = stageManager.loadView(EditTaskPane.class);
                Scene scene = new Scene(node);
                scene.setFill(Color.TRANSPARENT);
                editTaskStage.setScene(scene);
                editTaskStage.initStyle(StageStyle.TRANSPARENT);
                editTaskStage.initModality(Modality.APPLICATION_MODAL);
                editTaskStage.show();
            }
        });

        ObservableList<PieChart.Data> pieChartData =
                        FXCollections.observableArrayList(getPieChartList());
        doughnutChart.getData().addAll(pieChartData);
        doughnutChart.getStylesheets().add(
                getClass().getResource("/styles/doughnutChart.css").toExternalForm());
        doughnutChart.setLegendVisible(false);
        doughnutChart.setLabelsVisible(false);

        openTaskCntTxt.setText(String.valueOf(lvOpen.getItems().size()));
        workingTaskCntTxt.setText(String.valueOf(lvWorking.getItems().size()));
        postponedTaskCntTxt.setText(String.valueOf(lvPostponed.getItems().size()));
        completedTaskCntTxt.setText(String.valueOf(lvCompleted.getItems().size()));

        proTitleTxt.setText(activeProject.getProTitle());
        proDescTxt.setText(activeProject.getProDescription());
    }

    List<PieChart.Data> getPieChartList(){
        List<PieChart.Data> pieChartDataList = new ArrayList<>();

        statusDictionary.forEach(status -> {
            List<Task> proT = taskRepo.findByTaskUserIdAndTaskProId(user.getUserId(), activeProject.getProId());
            System.out.println(proT);
            List<Task> filteredTask = proT.stream().filter( proTask -> (status.getDictId() == proTask.getTaskStatus())).collect(Collectors.toList());
            PieChart.Data pieData = new PieChart.Data(status.getDictName(), filteredTask.size());
            pieChartDataList.add(pieData);
        });

        return pieChartDataList;
    }

    List<Dict> getDictByDictType(String dictTypeTxt){
        DictType dictType = dictTypeRepo.findByDtTypeName(dictTypeTxt);
        List<Dict> dictList = dictRepo.findDictByDictTypeNo(dictType.getDtTypeNo());
        return dictList;
    }

}
