package com.example.micro_wins;

import javafx.stage.Stage;
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
    }
}
