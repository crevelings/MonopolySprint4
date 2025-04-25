package org.monopoly.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.monopoly.Model.Game;
import org.monopoly.Model.Players.Player;
import org.monopoly.View.GameScene.GameScene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * GUI class for the Monopoly game.
 * This class is responsible for creating and managing the GUI scenes
 * as well as their respective components.
 * @author walshj05
 */
public class GUI {
    private final Stage stage;
    private static GUI instance;

    /**
     * Returns the singleton instance of the GUI class.
     * @return The GUI instance, or null if it hasn't been created yet.
     */
    public static GUI getInstance() {
        return instance; // returns null if the GUI hasn't been made yet
    }

    /**
     * Constructor for the GUI class.
     * Initializes the GUI with a given stage and shows the startup screen.
     * @param stage The stage to be used for the GUI.
     * @throws IOException If an error occurs while loading the FXML files.
     * @author walshj05
     * @author crevelings (4/15/25)
     * 4/15/25: Goes right to startup screen instead of going straight to GameScene
     */
    public GUI(Stage stage) throws IOException {
        instance = this;
        this.stage = stage;
        showStartupScreen();
    }

    /**
     * Shows the startup screen where players can configure the game.
     * @throws IOException If an error occurs while loading the FXML file.
     * @author crevelings
     */
    private void showStartupScreen() throws IOException {
        String fxmlPath = "/org/monopoly/View/GameScene/Startup/startup.fxml";
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
        stage.setTitle("Monopoly - Startup");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    /**
     * Sets the game scene for the GUI.
     * This method creates a new GameScene instance and sets it as the current scene.
     * @param humanPlayers The list of human players in the game.
     * @param computerPlayers The list of computer players in the game.
     * @throws IOException If an error occurs while loading the FXML files.
     * @author walshj05
     * @author crevelings (4/15/25)
     * 4/15/25: Calls gamescene object inside of method instead of one of the field variables
     *
     */
    public void setGameScene(ArrayList<Player> humanPlayers, ArrayList<Player> computerPlayers) throws IOException {
        // Create a new game scene and set it as the current scene
        GameScene gameScene = new GameScene(humanPlayers, computerPlayers);
        Game game = new Game(humanPlayers, computerPlayers);
        game.start();
        stage.setScene(gameScene.getScene());
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

}
