package org.monopoly.View.GameScene.Board;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.monopoly.Model.Players.ComputerPlayer;
import org.monopoly.Model.Players.HumanPlayer;
import org.monopoly.Model.Players.Player;
import org.monopoly.Model.Players.Token;
import org.monopoly.View.GUI;

import java.util.ArrayList;

/**
 * Controller for the startup screen where players can configure the game settings.
 * @author crevelings
 */
public class StartupController {
    @FXML
    private ComboBox<String> humanPlayerCount;

    @FXML
    private ComboBox<String> cpuPlayerCount;

    @FXML
    private Label errorMessage;

    @FXML
    private Button startGameButton;

    /**
     * Initializes the controller with default number of players.
     * Sets minimum required number of players for CPU and Human.
     * @author crevelings
     */
    @FXML
    public void initialize() {
        humanPlayerCount.setValue("1");
        cpuPlayerCount.setValue("1");
        errorMessage.setText("");
    }

    /**
     * Validates the total number of players and their distribution.
     * @return true if the configuration is valid, false otherwise
     */
    private boolean validatePlayerCounts() {
        int humans = Integer.parseInt(humanPlayerCount.getValue());
        int cpus = Integer.parseInt(cpuPlayerCount.getValue());
        int total = humans + cpus;

        if (total < 2) {
            errorMessage.setText("Must have at least 2 players total");
            return false;
        }
        if (total > 4) {
            errorMessage.setText("Cannot have more than 4 players total");
            return false;
        }
        if (humans < 1) {
            errorMessage.setText("Must have at least 1 human player");
            return false;
        }
        if (cpus < 1) {
            errorMessage.setText("Must have at least 1 CPU player");
            return false;
        }

        errorMessage.setText("");
        return true;
    }

    /**
     * Starts up GameBoard and handles getting number of players (Human and CPU players)
     * Also calls GUI instance so that GUI class can handle transition as well.
     * @param event The action event triggered by the start button
     */
    @FXML
    public void launchGameBoard(ActionEvent event) {
        try {
            if (!validatePlayerCounts()) {
                return;
            }

            int humans = Integer.parseInt(humanPlayerCount.getValue());
            int cpus = Integer.parseInt(cpuPlayerCount.getValue());
            
            ArrayList<Player> humanPlayers = new ArrayList<>();
            ArrayList<Player> computerPlayers = new ArrayList<>();
            
            // Create human players
            for (int i = 1; i <= humans; i++) {
                humanPlayers.add(new HumanPlayer("Player " + i, 
                    new Token("Player " + i, "testingApple.png")));
            }
            
            // Create CPU players
            for (int i = 1; i <= cpus; i++) {
                computerPlayers.add(new ComputerPlayer("CPU " + i, 
                    new Token("CPU " + i, "TopHat.png")));
            }

            // Get the GUI instance and transition to game scene
            GUI gui = GUI.getInstance();
            if (gui != null) {
                gui.setGameScene(humanPlayers, computerPlayers);
            }
        } catch (Exception e) {
            errorMessage.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
