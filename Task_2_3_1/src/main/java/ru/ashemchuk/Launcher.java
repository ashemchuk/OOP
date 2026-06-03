package ru.ashemchuk;

import javafx.application.Application;

/**
 * Launcher class that serves as the entry point for the JavaFX application.
 * Required for some build tools (e.g., Maven/Gradle) to properly start JavaFX.
 */
public class Launcher {
    /**
     * Main method that delegates to JavaFX's {@link Application#launch}.
     *
     * @param args command‑line arguments
     */
    public static void main(String[] args) {
        Application.launch(SnakeGameApplication.class, args);
    }
}
