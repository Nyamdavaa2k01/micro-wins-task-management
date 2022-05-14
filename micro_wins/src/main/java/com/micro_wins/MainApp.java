/**
 * @author Nyamka
 * @project micro-wins-task-management
 */

package com.micro_wins;

import com.micro_wins.model.Task;
import com.micro_wins.repository.TaskRepo;
import com.micro_wins.view.StageManager;
import com.micro_wins.view.login.LoginPane;
import com.micro_wins.view.main.MainPane;
import com.micro_wins.view.main.TodayPane;
import javafx.application.Application;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApp implements ApplicationListener<StageReadyEvent> {

    private static StageManager STAGE_MANAGER;

    private static Locale LOCALE = Locale.ENGLISH;

    @Autowired
    ConfigurableApplicationContext springAppContext;

    // 1. runs Java application. Neither SpringContext nor JavaFx context is initialized in this stage
    public static void main(String[] args)
    {
        // 2. Start JavaFX application in another Thread (by calling com.sun.javafx.application.LauncherImpl#run())
        Application.launch(SpringBootJavaFxApplication.class, args);
    }

    // 6. callback method. Catching event produced by SpringBootJavaFxApplication#start() method, once the initialization of Spring context, JavaFx context and FxWeaver context is done
    @Override
    public void onApplicationEvent(StageReadyEvent event)
    {
        STAGE_MANAGER = springAppContext.getBean(StageManager.class, event.stage);
        STAGE_MANAGER.rebuildStage(TodayPane.class);
    }

    public static StageManager getStageManager()
    {
        return STAGE_MANAGER;
    }

    public static void setLOCALE(Locale locale)
    {
        MainApp.LOCALE = locale;
    }

    public static Locale getLOCALE()
    {
        return LOCALE;
    }

}