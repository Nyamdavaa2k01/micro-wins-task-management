/**
 * @author Nyamka
 * @project micro-wins-task-management
 */

package com.micro_wins;

import com.micro_wins.view.StageManager;
import com.micro_wins.view.login.LoginPane;
import javafx.application.Application;

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
        STAGE_MANAGER.rebuildStage(LoginPane.class);
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

//    private ConfigurableApplicationContext applicationContext;
//    @Override
//    public void init() {
//        applicationContext = SpringApplication.run(MainController.class);
//    }
//
//    @Override
//    public void stop() {
//        applicationContext.close();
//    }
//
//    @Override
//    public void start(Stage stage) throws IOException, SQLException {
//
//        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/micro_wins", "root", "root") ;
//        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MainController.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1366, 700);
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setResizable(true);
//        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds() ;
//        stage.setX(primaryScreenBounds.getMinX());
//        stage.setY(primaryScreenBounds.getMinY());
//        stage.setWidth(primaryScreenBounds.getWidth());
//        stage.setHeight(primaryScreenBounds.getHeight());
//        stage.setScene(scene);
//        stage.show();
//        ResizeHelper.addResizeListener(stage);
//    }
//
//    public static void main(String[] args) {
//        launch();
//    }

}