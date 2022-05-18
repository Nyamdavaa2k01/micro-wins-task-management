package com.micro_wins.modal;

import com.micro_wins.constant.ConstantStyles;
import com.micro_wins.model.Dict;
import com.micro_wins.model.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 17/05/2022 - 12:52 PM
 */
public enum CustomPopup {
    POPUP;

    public void callSetPriorityPopup(int priorityButtonsStageInitPosX, int priorityButtonsStageInitPosY, List<Dict> dictList, Button setPriorityBtn, Task task){

        VBox priorityButtonsRoot = new VBox() ;
        Scene priorityButtonsScene = new Scene(priorityButtonsRoot, 200, 172) ;
        Stage priorityButtonsStage = new Stage();
        priorityButtonsStage.initStyle(StageStyle.UNDECORATED);

        ConstantStyles constantStyles = new ConstantStyles();

        priorityButtonsStage.setX(priorityButtonsStageInitPosX);
        priorityButtonsStage.setY(priorityButtonsStageInitPosY);

        Button[] priorityButtons = new Button[4] ;

        int i ;
        for (i = 0 ; i < 4 ; i ++) {
            priorityButtons[i] = new Button() ;
            priorityButtons[i].setAlignment(Pos.TOP_LEFT);
            priorityButtons[i].setStyle(constantStyles.getDEFAULT_BUTTON_STYLE());
            priorityButtons[i].setOnAction(e -> {
                String text = ((Button) e.getSource()).getText();
                Dict dict = dictList.stream()
                        .filter(d -> text.equals(d.getDictName()))
                        .findAny()
                        .orElse(null);
                int priority = dict.getDictId();
                task.setTaskPriority(priority);
                setPriorityBtn.setGraphic(((Button) e.getSource()).getGraphic());
                ((Stage)((Button)e.getSource()).getScene().getWindow()).close();
            });
            String buttonText = dictList.get(i).getDictName();
            String buttonGraphicFilePath ="file:micro_wins/src/main/resources/images/priority-"+(i+1)+"-icon.png" ;
            ImageView buttonGraphicImage = new ImageView(new Image(buttonGraphicFilePath)) ;
            buttonGraphicImage.setFitHeight(25);
            buttonGraphicImage.setFitWidth(25);
            priorityButtons[i].setText(buttonText);
            priorityButtons[i].setGraphic(buttonGraphicImage);
            priorityButtons[i].setPrefWidth(200);
            priorityButtons[i].setPrefHeight(43);
            priorityButtonsRoot.getChildren().add(priorityButtons[i]) ;
        }
        priorityButtonsStage.setScene(priorityButtonsScene);
        priorityButtonsStage.initModality(Modality.APPLICATION_MODAL);
        priorityButtonsStage.show();
    }
}
