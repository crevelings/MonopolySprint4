package org.monopoly.View;


import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class to run the Monopoly game
 * @author walshj05
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        new GUI(stage);
    }

    /**
     * Main method to launch the application
     * @param args command line arguments
     * Came with the original project repository
     */
    public static void main(String[] args) {
        launch();
    }
}
