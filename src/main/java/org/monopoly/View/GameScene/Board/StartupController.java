package org.monopoly.View.GameScene.Board;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.monopoly.Model.Players.ComputerPlayer;
import org.monopoly.Model.Players.HumanPlayer;
import org.monopoly.Model.Players.Player;
import org.monopoly.Model.Players.Token;
import org.monopoly.View.GUI;

import java.util.ArrayList;

public class StartupController {
    @FXML
    private ComboBox<String> playerCountComboBox;

    @FXML
    private VBox playerSetupContainer;

    @FXML
    private Button startGameButton;

    /**
     * Sets minimum required number of players to 2.
     * @author crevelings
     */
    @FXML
    public void initialize() {
        if (playerCountComboBox != null) {
            playerCountComboBox.setValue("2");
        }
    }

    /**
     * Starts up GameBoard and handles getting number of players (Human and CPU players)
     * Also calls GUI instance so that GUI class can handle transition as well.
     * @param event
     */
    @FXML
    public void launchGameBoard(ActionEvent event) {
        try {
            int playerCount = Integer.parseInt(playerCountComboBox.getValue());
            
            // Create sample players (you can modify this based on your needs)
            ArrayList<Player> humanPlayers = new ArrayList<>();
            ArrayList<Player> computerPlayers = new ArrayList<>();
            
            // Add human players
            humanPlayers.add(new HumanPlayer("Player 1", new Token("Player 1", "testingApple.png")));
            if (playerCount > 2) {
                humanPlayers.add(new HumanPlayer("Player 2", new Token("Player 2", "testingBanana.png")));
            }
            
            // Add computer players
            computerPlayers.add(new ComputerPlayer("Computer 1", new Token("Computer 1", "TopHat.png")));
            if (playerCount > 3) {
                computerPlayers.add(new ComputerPlayer("Computer 2", new Token("Computer 2", "Boot.png")));
            }

            // Get the GUI instance and transition to game scene
            GUI gui = GUI.getInstance();
            if (gui != null) {
                gui.setGameScene(humanPlayers, computerPlayers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
