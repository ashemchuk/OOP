package ru.ashemchuk;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX application entry point for the Snake game.
 * Loads the FXML UI definition and displays the main window.
 */
public class SnakeGameApplication extends Application {
    /**
     * Starts the JavaFX application.
     *
     * @param stage the primary stage for this application
     * @throws IOException if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader =
            new FXMLLoader(SnakeGameApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);
        stage.setTitle("Snake");
        stage.setScene(scene);
        // Set minimum window size to prevent shrinking too small
        stage.setMinWidth(300);
        stage.setMinHeight(400);
        stage.show();
    }
}