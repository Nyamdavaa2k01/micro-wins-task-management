package com.micro_wins.modal;

import com.micro_wins.constant.ConstantColors;
import com.micro_wins.constant.Functions;
import com.micro_wins.model.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 17/05/2022 - 9:52 AM
 */
public enum ProjectTaskCard {
    PRO_TASK_CARD;

    public VBox getProOpenTaskCard(Task item){
        Functions dateToString = Functions.DATE_TO_STRING;
        Functions hexToRgb = Functions.HEX_TO_RGB;
        ConstantColors constantColors = new ConstantColors();
        VBox taskCard = new VBox();
        taskCard.setStyle("-fx-background-color: #fff; -fx-border-color: #8980ef; -fx-border-width: 1px; -fx-border-radius: 30px; -fx-background-radius: 30px;");
        taskCard.setPadding(new Insets(5));
        taskCard.setPrefHeight(60);
        taskCard.setSpacing(5);

        HBox top = new HBox();
        top.setAlignment(Pos.CENTER);
        top.setSpacing(10);
        Circle statusCircle = new Circle(8);
        statusCircle.setStrokeWidth(3);

        String priorityColor = constantColors.getPRIORITY4_COLOR();
        if(item.getTaskPriority() == 7){
            priorityColor = constantColors.getPRIORITY1_COLOR();
        }else if(item.getTaskPriority() == 8){
            priorityColor = constantColors.getPRIORITY2_COLOR();
        }else if(item.getTaskPriority() == 9){
            priorityColor = constantColors.getPRIORITY3_COLOR();
        }

        statusCircle.setFill(hexToRgb.hexToRgb(priorityColor, 0.4));
        statusCircle.setStroke(hexToRgb.hexToRgb(priorityColor, 1));

        Label taskNameLbl = new Label(item.getTaskTitle());
        taskNameLbl.setWrapText(true);
        taskNameLbl.setPrefWidth(80);
        taskNameLbl.setTextFill(Color.BLACK);

        Image taskUserImg = new Image("file:micro_wins/src/main/resources/images/clarity_assign_user_line.png");
        ImageView taskUserImgView = new ImageView(taskUserImg);
        taskUserImgView.setFitHeight(20);
        taskUserImgView.setFitWidth(20);

        top.getChildren().addAll(statusCircle, taskNameLbl, taskUserImgView);

        HBox bottom = new HBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(5);
        Label taskStartDate = new Label(dateToString.dateToLocalDate(item.getTaskStartDate()).toString());
        taskStartDate.setTextFill(Color.BLACK);
        taskStartDate.setPadding(new Insets(0, 2, 0, 2));
        taskStartDate.setStyle("-fx-background-color: #fff; -fx-border-color: #000; -fx-border-width: 1px; -fx-border-radius: 30px; -fx-background-radius: 30px;");
        Image arrowRight = new Image("file:micro_wins/src/main/resources/images/ci_sub_right.png");
        ImageView arrowRightView = new ImageView(arrowRight);
        arrowRightView.setFitHeight(15);
        arrowRightView.setFitWidth(30);
        bottom.getChildren().addAll(arrowRightView, taskStartDate);

        taskCard.getChildren().addAll(top, bottom);
        return taskCard;
    }

    public VBox getProWorkingTaskCard(Task item){
        Functions dateToString = Functions.DATE_TO_STRING;
        Functions hexToRgb = Functions.HEX_TO_RGB;
        ConstantColors constantColors = new ConstantColors();
        VBox taskCard = new VBox();
        taskCard.setStyle("-fx-background-color: #fff; -fx-border-color: #f25333; -fx-border-width: 1px; -fx-border-radius: 30px; -fx-background-radius: 30px;");
        taskCard.setPadding(new Insets(5));
        taskCard.setPrefHeight(60);
        taskCard.setSpacing(5);

        HBox top = new HBox();
        top.setAlignment(Pos.CENTER);
        top.setSpacing(10);
        Circle statusCircle = new Circle(8);
        statusCircle.setStrokeWidth(3);
        String priorityColor = constantColors.getPRIORITY4_COLOR();

        if(item.getTaskPriority() == 7){
            priorityColor = constantColors.getPRIORITY1_COLOR();
        }else if(item.getTaskPriority() == 8){
            priorityColor = constantColors.getPRIORITY2_COLOR();
        }else if(item.getTaskPriority() == 9){
            priorityColor = constantColors.getPRIORITY3_COLOR();
        }

        statusCircle.setFill(hexToRgb.hexToRgb(priorityColor, 0.4));
        statusCircle.setStroke(hexToRgb.hexToRgb(priorityColor, 1));

        Label taskNameLbl = new Label(item.getTaskTitle());
        taskNameLbl.setWrapText(true);
        taskNameLbl.setPrefWidth(80);
        taskNameLbl.setTextFill(Color.BLACK);

        Image taskUserImg = new Image("file:micro_wins/src/main/resources/images/freelance.png");
        ImageView taskUserImgView = new ImageView(taskUserImg);
        taskUserImgView.setFitHeight(20);
        taskUserImgView.setFitWidth(20);

        top.getChildren().addAll(statusCircle, taskNameLbl, taskUserImgView);

        HBox bottom = new HBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(5);
        Label taskStartDate = new Label(dateToString.dateToLocalDate(item.getTaskStartDate()).toString());
        taskStartDate.setTextFill(Color.BLACK);
        taskStartDate.setPadding(new Insets(0, 2, 0, 2));
        taskStartDate.setStyle("-fx-background-color: #fff; -fx-border-color: #000; -fx-border-width: 1px; -fx-border-radius: 30px; -fx-background-radius: 30px;");
        Image arrowRight = new Image("file:micro_wins/src/main/resources/images/ci_sub_right.png");
        ImageView arrowRightView = new ImageView(arrowRight);
        arrowRightView.setFitHeight(15);
        arrowRightView.setFitWidth(30);
        bottom.getChildren().addAll(arrowRightView, taskStartDate);

        taskCard.getChildren().addAll(top, bottom);
        return taskCard;
    }

    public VBox getProPostponedTaskCard(Task item){
        Functions dateToString = Functions.DATE_TO_STRING;
        Functions hexToRgb = Functions.HEX_TO_RGB;
        ConstantColors constantColors = new ConstantColors();
        VBox taskCard = new VBox();
        taskCard.setStyle("-fx-background-color: #fff; -fx-border-color: #ff13bf; -fx-border-width: 1px; -fx-border-radius: 30px; -fx-background-radius: 30px;");
        taskCard.setPadding(new Insets(5));
        taskCard.setPrefHeight(60);
        taskCard.setSpacing(5);

        HBox top = new HBox();
        top.setAlignment(Pos.CENTER);
        top.setSpacing(10);
        Circle statusCircle = new Circle(8);
        statusCircle.setStrokeWidth(3);
        String priorityColor = constantColors.getPRIORITY4_COLOR();

        if(item.getTaskPriority() == 7){
            priorityColor = constantColors.getPRIORITY1_COLOR();
        }else if(item.getTaskPriority() == 8){
            priorityColor = constantColors.getPRIORITY2_COLOR();
        }else if(item.getTaskPriority() == 9){
            priorityColor = constantColors.getPRIORITY3_COLOR();
        }

        statusCircle.setFill(hexToRgb.hexToRgb(priorityColor, 0.4));
        statusCircle.setStroke(hexToRgb.hexToRgb(priorityColor, 1));

        Label taskNameLbl = new Label(item.getTaskTitle());
        taskNameLbl.setWrapText(true);
        taskNameLbl.setPrefWidth(80);
        taskNameLbl.setTextFill(Color.BLACK);

        Image taskUserImg = new Image("file:micro_wins/src/main/resources/images/danger.png");
        ImageView taskUserImgView = new ImageView(taskUserImg);
        taskUserImgView.setFitHeight(18);
        taskUserImgView.setFitWidth(18);

        top.getChildren().addAll(statusCircle, taskNameLbl, taskUserImgView);

        HBox bottom = new HBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(5);
        Label taskStartDate = new Label(dateToString.dateToLocalDate(item.getTaskStartDate()).toString());
        taskStartDate.setTextFill(Color.BLACK);
        taskStartDate.setPadding(new Insets(0, 2, 0, 2));
        taskStartDate.setStyle("-fx-background-color: #fff; -fx-border-color: #f00; -fx-border-width: 1px; -fx-border-radius: 30px; -fx-background-radius: 30px;");
        Image arrowRight = new Image("file:micro_wins/src/main/resources/images/ci_sub_right.png");
        ImageView arrowRightView = new ImageView(arrowRight);
        arrowRightView.setFitHeight(15);
        arrowRightView.setFitWidth(30);
        bottom.getChildren().addAll(arrowRightView, taskStartDate);

        taskCard.getChildren().addAll(top, bottom);
        return taskCard;
    }

    public VBox getProCompletedTaskCard(Task item){
        Functions dateToString = Functions.DATE_TO_STRING;
        Functions hexToRgb = Functions.HEX_TO_RGB;
        ConstantColors constantColors = new ConstantColors();
        VBox taskCard = new VBox();
        taskCard.setStyle("-fx-background-color: #fff; -fx-border-color: #4caf50; -fx-border-width: 1px; -fx-border-radius: 30px; -fx-background-radius: 30px;");
        taskCard.setPadding(new Insets(5));
        taskCard.setPrefHeight(60);
        taskCard.setSpacing(5);

        HBox top = new HBox();
        top.setAlignment(Pos.CENTER);
        top.setSpacing(10);
        Circle statusCircle = new Circle(8);
        statusCircle.setStrokeWidth(3);
        String priorityColor = constantColors.getPRIORITY4_COLOR();

        if(item.getTaskPriority() == 7){
            priorityColor = constantColors.getPRIORITY1_COLOR();
        }else if(item.getTaskPriority() == 8){
            priorityColor = constantColors.getPRIORITY2_COLOR();
        }else if(item.getTaskPriority() == 9){
            priorityColor = constantColors.getPRIORITY3_COLOR();
        }

        statusCircle.setFill(hexToRgb.hexToRgb(priorityColor, 0.4));
        statusCircle.setStroke(hexToRgb.hexToRgb(priorityColor, 1));

        Label taskNameLbl = new Label(item.getTaskTitle());
        taskNameLbl.setWrapText(true);
        taskNameLbl.setPrefWidth(80);
        taskNameLbl.setTextFill(Color.BLACK);
        taskNameLbl.getStylesheets().add(
                getClass().getResource("/styles/completedTask.css").toExternalForm());

        Image taskUserImg = new Image("file:micro_wins/src/main/resources/images/checked.png");
        ImageView taskUserImgView = new ImageView(taskUserImg);
        taskUserImgView.setFitHeight(16);
        taskUserImgView.setFitWidth(16);

        top.getChildren().addAll(statusCircle, taskNameLbl, taskUserImgView);

        HBox bottom = new HBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(5);
        Label taskStartDate = new Label(dateToString.dateToLocalDate(item.getTaskStartDate()).toString());
        taskStartDate.setTextFill(Color.BLACK);
        taskStartDate.setPadding(new Insets(0, 2, 0, 2));
        taskStartDate.setStyle("-fx-background-color: #fff; -fx-border-color: #000; -fx-border-width: 1px; -fx-border-radius: 30px; -fx-background-radius: 30px;");
        Image arrowRight = new Image("file:micro_wins/src/main/resources/images/ci_sub_right.png");
        ImageView arrowRightView = new ImageView(arrowRight);
        arrowRightView.setFitHeight(15);
        arrowRightView.setFitWidth(30);
        bottom.getChildren().addAll(arrowRightView, taskStartDate);

        taskCard.getChildren().addAll(top, bottom);
        return taskCard;
    }
}
