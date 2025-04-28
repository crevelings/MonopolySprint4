package org.monopoly.View.GameScene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.monopoly.Model.Players.ComputerPlayer;
import org.monopoly.Model.Players.Player;
import org.monopoly.Model.Players.Token;
import org.monopoly.View.GameScene.Board.BoardController;
import org.monopoly.View.ComputerPlayerController;
import org.monopoly.View.GameScene.Board.BottomPanelController;
import org.monopoly.View.HumanPlayerController;

import java.io.IOException;
import java.util.ArrayList;

/**
 * GameScene class that represents the main game scene.
 * This class is responsible for creating the game scene and initializing the player interfaces.
 * @author walshj05
 */
public class GameScene {
    private final Scene scene;
    private final BoardController boardController;
    private final BottomPanelController bottomController;
    private static GameScene instance;
    public static GameScene getInstance() {
        return  instance; // returns null if the GUI hasn't been made yet
    }

    /**
     * Constructor for the GameScene class.
     * @param humanPlayers The list of human players.
     * @param computerPlayers The list of computer players.
     * @throws IOException if an error occurs while loading the FXML files
     * @author walshj05
     */
    public GameScene(ArrayList<Player> humanPlayers, ArrayList<Player> computerPlayers) throws IOException {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1190, 1000);
        instance = this;

        FXMLLoader bottomLoader = new FXMLLoader(GameScene.class.getResource("BottomPanel.fxml"));
        Parent bottomRoot = bottomLoader.load();
        this.bottomController = bottomLoader.getController();
        AnchorPane.setBottomAnchor(bottomRoot, 0.0);
        AnchorPane.setLeftAnchor(bottomRoot, 100.0);
        // Load the board
        FXMLLoader boardLoader = new FXMLLoader(GameScene.class.getResource("BoardPane.fxml"));
        Parent boardRoot = boardLoader.load();
        this.boardController = boardLoader.getController();
        root.getChildren().addAll(boardRoot, bottomRoot);
        initializePlayerInterfaces(humanPlayers, computerPlayers, root);
        this.scene = new Scene(root, 1190, 1000);
    }

    /**
     * Initializes the player interfaces for both human and computer players.
     * @param humanPlayers The list of human players.
     * @param computerPlayers The list of computer players.
     * @param root The root pane to add the player interfaces to.
     * @throws IOException if an error occurs while loading the FXML files
     */
    private void initializePlayerInterfaces(ArrayList<Player> humanPlayers, ArrayList<Player> computerPlayers, AnchorPane root) throws IOException {
        // Initialize the board with players
        int yStart = 0;
        for (Player player : humanPlayers) {
            FXMLLoader playerLoader = new FXMLLoader(GameScene.class.getResource("HumanPlayerInterface.fxml"));
            Parent playerRoot = playerLoader.load();
            HumanPlayerController playerController = playerLoader.getController();
            // todo add controller to players observer list
            playerController.setPlayer(player);
            playerRoot.setLayoutY(yStart);
            playerRoot.setLayoutX(740);
            yStart += 185;
            root.getChildren().add(playerRoot);
        }

        for (Player player : computerPlayers) {
            FXMLLoader playerLoader = new FXMLLoader(GameScene.class.getResource("ComputerPlayerInterface.fxml"));
            Parent playerRoot = playerLoader.load();
            ComputerPlayerController playerController = playerLoader.getController();
            // todo add controller to players observer list
            playerController.setPlayer((ComputerPlayer) player);
            playerRoot.setLayoutY(yStart);
            playerRoot.setLayoutX(740);
            root.getChildren().add(playerRoot);
            yStart += 185;
        }
    }


    /**
     * Updates the tokens on the board.
     * @param tokens The list of tokens to update.
     * @param tileIndex The index of the tile to update.
     */
    public void updateTokens(ArrayList<Token> tokens, int tileIndex) {
        // Ensure index is not a rollover index
        if (tileIndex > 39) {
            tileIndex = tileIndex % 40;
        }
        boardController.updateTokens(tokens, tileIndex);
    }

    /**
     * Returns the game scene controller.
     * @return The board controller.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Adds the file path to the given file name.
     * @param fileName The name of the file.
     * @return The full file path.
     * @author walshj05
     */
    public static String addFilePath(String fileName) {
        // Check if the file exists in the resources directory
        if (GameScene.class.getResource("TokenPNGs/" + fileName) == null) {
            return "file:src/main/resources/org/monopoly/View/GameScene/TokenPNGs/error.png";
        }
        return "file:src/main/resources/org/monopoly/View/GameScene/TokenPNGs/" + fileName;
    }

    /**
     * Updates the dice on the board.
     * @param die1 The value of the first die.
     * @param die2 The value of the second die.
     * @author walshj05
     */
    public void updateDice(int die1, int die2) {
        boardController.updateDice(die1, die2);
    }

    /**
     * Sends an alert message to the user.
     * @param message The message to send.
     *
     * Developed by: shifmans
     */
    public static void sendAlert(String message) {
        sendAlert(message, "Turn Event!");
    }


    public static void sendAlert(String message, String title) {
        if (instance != null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("\u26A0 " + title);
            alert.setGraphic(new ImageView(new Image(addFilePath("monopolyMan.png"), 200, 200, false, false)));
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.setX(650);
            alert.setY(400);
            alert.showAndWait();
        }
    }
}
