package org.monopoly.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.monopoly.Model.Dice;
import org.monopoly.Model.Players.Player;
import org.monopoly.View.GameScene.GameScene;

/**
 * Controller for the human player interface.
 * This class handles the player's actions and updates the UI accordingly.
 * @author walshj05
 */
public class HumanPlayerController {
    @FXML
    public Button mortProp;
    @FXML
    public Button getOutOfJail;
    @FXML
    public Button trade;
    @FXML
    public Button auction;
    @FXML
    public Button drawCard;
    @FXML
    private AnchorPane playerPane;
    @FXML
    private Button rollDice;
    @FXML
    private Button sell;
    @FXML
    private Button buyProp;
    @FXML
    private Button buyHouse;
    @FXML
    private Button buyHotel;
    @FXML
    private Button endTurn;
    @FXML
    private Label name;
    @FXML
    private ImageView token;
    @FXML
    private Label money;
    @FXML
    private VBox properties;
    private Player player;


    /**
     * Initializes the player interface with the given player.
     * @param player The player to initialize the interface for.
     * @author walshj05
     */
    public void setPlayer(Player player) {
        this.player = player;
        name.setText(player.getName());
        token.setImage(new Image(GameScene.addFilePath(player.getToken().getIcon())));
        name.setText(player.getName());
        money.setText("Balance: $" + player.getBalance());
    }

    /**
     * Sets the token image for the player.
     * @author walshj05
     */
    public void updateProperties(){}

    /**
     * Updates the players information
     * @author walshj05
     */
    public void updatePlayerInfo(){
        money.setText("Balance: $" + player.getBalance());

    }

    /**
     * Handles the action when the player clicks the "Roll Dice" button.
     * @param actionEvent The action event triggered by the button click.
     * @author walshj05
     */
    public void onRollDice(ActionEvent actionEvent) {
        player.takeTurn(Dice.getInstance());
    }
}
