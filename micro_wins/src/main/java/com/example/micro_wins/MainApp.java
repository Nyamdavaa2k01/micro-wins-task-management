package com.example.micro_wins;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.stage.StageStyle;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class MainApp extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        applicationContext = SpringApplication.run(MainController.class);
    }

    @Override
    public void stop() {
        applicationContext.close();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1366, 700);
        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setMaximized(true);
        stage.setResizable(true);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds() ;
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
        stage.setScene(scene);
        stage.show();
        ResizeHelper.addResizeListener(stage);
    }

    public static void main(String[] args) {
        launch();
    }

}