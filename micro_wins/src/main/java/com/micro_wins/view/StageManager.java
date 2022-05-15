package com.micro_wins.view;

import com.micro_wins.utils.ResizeHelper;
import com.micro_wins.config.ResourceBundleUtil;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    private Stage secondaryStage ;

    public StageManager(Stage stage)
    {
        this.primaryStage = stage;
        secondaryStage = null;
    }

    public void rebuildStage(Class<? extends FxController> fxControllerClass)
    {
        Scene scene = createScene(primaryStage, fxControllerClass);
        scene.setFill(Color.TRANSPARENT);
        showScene(fxControllerClass, scene);
    }

    public void addStage (Class<? extends FxController> fxControllerClass) {
        secondaryStage = new Stage( );
        Scene scene = createScene(secondaryStage, fxControllerClass) ;
        scene.setFill(Color.TRANSPARENT);
        showSecondaryScene(fxControllerClass, scene);
    }

    public void closeSecondaryStage () {
        secondaryStage.close();
    }

    private Scene createScene(Stage stage , Class<? extends FxController> fxControllerClass)
    {
        Parent node = fxWeaver.loadView(fxControllerClass);
        Scene sc = stage.getScene();
        if (sc == null)
        {
            sc = new Scene(node);
        }
        sc.setRoot(node);
        return sc;
    }

    private void showScene(Class<? extends FxController> fxControllerClass, Scene scene)
    {
        String title = ResourceBundleUtil.getKey(fxControllerClass.getSimpleName() + ".title");
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        Rectangle2D screenBounds = Screen.getPrimary().getBounds() ;
        primaryStage.setWidth(screenBounds.getWidth());
        primaryStage.setHeight(screenBounds.getHeight());
        primaryStage.setX(screenBounds.getMinX()) ;
        primaryStage.setY(screenBounds.getMinY());

        try
        {
            primaryStage.show();
            ResizeHelper.addResizeListener(primaryStage);
        } catch (Exception exception)
        {
            logAndExit("Unable to show scene with title " + title, exception);
        }
    }

    private void showSecondaryScene(Class<? extends FxController> fxControllerClass, Scene scene)
    {
        String title = ResourceBundleUtil.getKey(fxControllerClass.getSimpleName() + ".title");
        secondaryStage.initStyle(StageStyle.UNDECORATED);
        secondaryStage.initStyle(StageStyle.TRANSPARENT);
        secondaryStage.setTitle(title);
        secondaryStage.setScene(scene);
        secondaryStage.sizeToScene();
        secondaryStage.centerOnScreen();
        try
        {
            secondaryStage.show();
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
