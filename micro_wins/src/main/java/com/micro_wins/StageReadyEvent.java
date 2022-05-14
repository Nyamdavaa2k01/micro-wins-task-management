package com.micro_wins;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.ApplicationEvent;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 11/05/2022 - 11:09 AM
 */
public class StageReadyEvent extends ApplicationEvent {
    public final Stage stage;

    public StageReadyEvent(Stage stage)
    {
        super(stage);
        this.stage = stage;
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
    }
}
