package com.fianteh;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        MainView mainView = new MainView();
        Label label = new Label("Hello, JavaFX ");
//        Scene scene = new Scene(new StackPane(label), 640, 480);
        Scene scene = new Scene(mainView, 1280, 960);
        stage.setScene(scene);
        stage.show();
        mainView.draw();
    }

    public static void main(String[] args) {
        launch();
    }

}