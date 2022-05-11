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
        System.out.println("\n\n rebuildStage 1");
        showScene(fxControllerClass, scene);
        System.out.println("\n\n rebuildStage 2");

    }

    private Scene createScene(Class<? extends FxController> fxControllerClass)
    {
        Parent node = fxWeaver.loadView(fxControllerClass);
        System.out.println("\n\n createScene 1");
        Scene sc = primaryStage.getScene();
        System.out.println("\n\n createScene 2");

        if (sc == null)
        {
            sc = new Scene(node);
            System.out.println("\n\n createScene 3");
        }
        System.out.println("\n\n createScene 4");
        sc.setRoot(node);
        System.out.println("\n\n createScene 5");
        return sc;
    }

    private void showScene(Class<? extends FxController> fxControllerClass, Scene scene)
    {
        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        String title = ResourceBundleUtil.getKey(fxControllerClass.getSimpleName() + ".title");
        System.out.println("\n\n showScene 1");
        primaryStage.setTitle(title);
        System.out.println("\n\n showScene 2");
        primaryStage.setScene(scene);
        System.out.println("\n\n showScene 3");
        primaryStage.sizeToScene();
        System.out.println("\n\n showScene 4");
        primaryStage.centerOnScreen();
        System.out.println("\n\n showScene 5");

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
        System.out.println("\n\n loadView 1");
        return fxWeaver.loadView(controllerClass, ResourceBundleUtil.getResourceBundle());
    }
}
