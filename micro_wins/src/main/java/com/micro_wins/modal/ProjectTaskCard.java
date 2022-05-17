package com.micro_wins.modal;

import javafx.scene.layout.HBox;

public enum ProjectTaskCard {
    PRO_OPEN_TASK_CARD,
    PRO_WORKING_TASK_CARD,
    PRO_POSTPONED_TASK_CARD,
    PRO_COMPLETED_TASK_CARD;

    public HBox getProOpenTaskCard(){
        return new HBox();
    }

    public HBox getProWorkingTaskCard(){
        return new HBox();
    }

    public HBox getProPostponedTaskCard(){
        return new HBox();
    }

    public HBox getProCompletedTaskCard(){
        return new HBox();
    }
}
