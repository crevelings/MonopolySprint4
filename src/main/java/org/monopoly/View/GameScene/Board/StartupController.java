package org.monopoly.View.GameScene.Board;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.monopoly.Model.Players.ComputerPlayer;
import org.monopoly.Model.Players.HumanPlayer;
import org.monopoly.Model.Players.Player;
import org.monopoly.Model.Players.Token;
import org.monopoly.View.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private VBox playerInputContainer;

    private final Map<String, TextField> playerNameFields = new HashMap<>();
    private final Map<String, ComboBox<String>> playerTokenFields = new HashMap<>();

    // Map of token display names to their image filenames
    private final Map<String, String> tokenImages = new HashMap<>() {{
        put("Car", "RaceCar.png");
        put("Dog", "ScottieDog.png");
        put("Hat", "TopHat.png");
        put("Ship", "BattleShip.png");
        put("Shoe", "Boot.png");
        put("Thimble", "Thimble.png");
        put("Wheelbarrow", "Wheelbarrow.png");
    }};

    private final String[] availableTokens = {
        "Car", "Dog", "Hat", "Ship", "Shoe", "Thimble", "Wheelbarrow"
    };

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
        updatePlayerInputs();
    }

    /**
     * Updates the player input fields based on the selected number of players.
     */
    @FXML
    public void updatePlayerInputs() {
        playerInputContainer.getChildren().clear();
        playerNameFields.clear();
        playerTokenFields.clear();

        int humanCount = Integer.parseInt(humanPlayerCount.getValue());
        int cpuCount = Integer.parseInt(cpuPlayerCount.getValue());

        // Add human player inputs
        for (int i = 1; i <= humanCount; i++) {
            addPlayerInputSection("Human Player " + i, true);
        }

        // Add CPU player inputs
        for (int i = 1; i <= cpuCount; i++) {
            addPlayerInputSection("CPU " + i, false);
        }
    }

    /**
     * Adds input fields for a player to the container.
     * @param playerLabel The label for the player section
     * @param isHuman Whether the player is human (affects name field editability)
     */
    private void addPlayerInputSection(String playerLabel, boolean isHuman) {
        VBox playerSection = new VBox(10);
        playerSection.setPadding(new Insets(10));
        playerSection.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 5;");

        Label sectionLabel = new Label(playerLabel);
        sectionLabel.setStyle("-fx-font-weight: bold;");

        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);

        // Name field
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField(playerLabel);
        nameField.setEditable(isHuman);
        playerNameFields.put(playerLabel, nameField);

        // Token selection
        Label tokenLabel = new Label("Token:");
        ComboBox<String> tokenBox = new ComboBox<>();
        tokenBox.getItems().addAll(availableTokens);
        tokenBox.setValue(availableTokens[0]);
        playerTokenFields.put(playerLabel, tokenBox);

        inputGrid.add(nameLabel, 0, 0);
        inputGrid.add(nameField, 1, 0);
        inputGrid.add(tokenLabel, 0, 1);
        inputGrid.add(tokenBox, 1, 1);

        playerSection.getChildren().addAll(sectionLabel, inputGrid);
        playerInputContainer.getChildren().add(playerSection);
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

        // Validate player names are not empty
        for (TextField field : playerNameFields.values()) {
            if (field.isEditable() && field.getText().trim().isEmpty()) {
                errorMessage.setText("Player names cannot be empty");
                return false;
            }
        }

        errorMessage.setText("");
        return true;
    }

    /**
     * Starts up GameBoard and handles getting number of players (Human and CPU players)
     * Also calls GUI instance so that GUI class can handle transition as well.
     * @author crevelings
     */
    @FXML
    public void launchGameBoard() {
        try {
            if (!validatePlayerCounts()) {
                return;
            }

            ArrayList<Player> humanPlayers = new ArrayList<>();
            ArrayList<Player> computerPlayers = new ArrayList<>();
            
            // Create human players
            for (int i = 1; i <= Integer.parseInt(humanPlayerCount.getValue()); i++) {
                String key = "Human Player " + i;
                String name = playerNameFields.get(key).getText();
                String tokenType = playerTokenFields.get(key).getValue();
                String tokenImage = tokenImages.get(tokenType); // Get the correct image filename
                humanPlayers.add(new HumanPlayer(name, new Token(name, tokenImage)));
            }
            
            // Create CPU players
            for (int i = 1; i <= Integer.parseInt(cpuPlayerCount.getValue()); i++) {
                String key = "CPU " + i;
                String name = playerNameFields.get(key).getText();
                String tokenType = playerTokenFields.get(key).getValue();
                String tokenImage = tokenImages.get(tokenType); // Get the correct image filename
                computerPlayers.add(new ComputerPlayer(name, new Token(name, tokenImage)));
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
