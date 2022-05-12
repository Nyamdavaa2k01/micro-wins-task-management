package com.example.micro_wins.view;

import com.example.micro_wins.config.ResourceBundleUtil;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import net.rgielen.fxweaver.core.FxWeaver;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 11/05/2022 - 10:38 AM
 */
public class StageManager {
    private static final Logger LOG = getLogger(StageManager.class);

    @Autowired
    private FxWeaver fxWeaver;

    private final Stage primaryStage;

    public StageManager(Stage stage)
    {
        this.primaryStage = stage;
    }

    public void rebuildStage(Class<? extends FxController> fxControllerClass)
    {
        Scene scene = createScene(fxControllerClass);
        showScene(fxControllerClass, scene);

    }

    private Scene createScene(Class<? extends FxController> fxControllerClass)
    {
        Parent node = fxWeaver.loadView(fxControllerClass);
        Scene sc = primaryStage.getScene();

        if (sc == null)
        {
            sc = new Scene(node);
        }
        sc.setRoot(node);
        return sc;
    }

    private void showScene(Class<? extends FxController> fxControllerClass, Scene scene)
    {
        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        String title = ResourceBundleUtil.getKey(fxControllerClass.getSimpleName() + ".title");
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();

        try
        {
            primaryStage.show();
        } catch (Exception exception)
        {
            logAndExit("Unable to show scene with title " + title, exception);
        }
    }

    private void logAndExit(String errorMsg, Exception exception)
    {
        LOG.error(errorMsg, exception, exception.getCause());
        Platform.exit();
    }

    public <C, V extends Node> V loadView(Class<C> controllerClass)
    {
        return fxWeaver.loadView(controllerClass, ResourceBundleUtil.getResourceBundle());
    }
}
