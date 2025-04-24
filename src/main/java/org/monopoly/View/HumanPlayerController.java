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
import org.monopoly.Model.Players.HumanPlayer;
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
    private HumanPlayer player;


    /**
     * Initializes the player interface with the given player.
     * @param player The player to initialize the interface for.
     * @author walshj05
     */
    public void setPlayer(Player player) {
        this.player = (HumanPlayer)player;
        name.setText(player.getName());
        token.setImage(new Image(GameScene.addFilePath(player.getToken().getIcon())));
        name.setText(player.getName());
        money.setText("Balance: $" + player.getBalance());
        this.player.setController(this);
    }

    /**
     * Sets the token image for the player.
     * @author walshj05
     */
    public void updateProperties(){
        properties.getChildren().clear();
        for (String property : player.getPropertiesOwned()){
            properties.getChildren().add(new Label(property));
        }
    }

    /**
     * Updates the players information
     * @author walshj05
     */
    public void updatePlayerInfo(){
        money.setText("Balance: $" + player.getBalance());
        updateProperties();
    }

    /**
     * Handles the action when the player clicks the "Roll Dice" button.
     * @param actionEvent The action event triggered by the button click.
     * @author walshj05
     */
    public void onRollDice(ActionEvent actionEvent) {
        player.rollDice(Dice.getInstance());

    }

    /**
     * Handles the action when the player clicks the "Sell" button.
     * @param actionEvent The action event triggered by the button click.
     * @author walshj05
     */
    public void onSell(ActionEvent actionEvent) {
        System.out.println(player.getName() + " Sell button clicked");
    }

    /**
     * Handles the action when the player clicks the "Buy Property" button.
     * @param actionEvent The action event triggered by the button click.
     * @author walshj05
     */
    public void onBuyProperty(ActionEvent actionEvent) {
        System.out.println(player.getName() + " Buy Property button clicked");
    }

    /**
     * Handles the action when the player clicks the "Buy House" button.
     * @param actionEvent The action event triggered by the button click.
     * @author walshj05
     */
    public void onBuyHouse(ActionEvent actionEvent) {
        System.out.println(player.getName() + " Buy House button clicked");
    }

    /**
     * Handles the action when the player clicks the "Buy Hotel" button.
     * @param actionEvent The action event triggered by the button click.
     * @author walshj05
     */
    public void onBuyHotel(ActionEvent actionEvent) {
        System.out.println(player.getName() + " Buy Hotel button clicked");
    }

    /**
     * Handles the action when the player clicks the "End Turn" button.
     * @param actionEvent The action event triggered by the button click.
     * @author walshj05
     */
    public void onEndTurn(ActionEvent actionEvent) {
        System.out.println(player.getName() + " End Turn button clicked");
    }

    /**
     * Handles the action when the player clicks the "Mortgage Property" button.
     * @param actionEvent The action event triggered by the button click.
     * @author walshj05
     */
    public void onDrawCard(ActionEvent actionEvent) {
        System.out.println(player.getName() + " Draw Card button clicked");
    }

    /**
     * Handles the action when the player clicks the "Get Out of Jail" button.
     * @param actionEvent The action event triggered by the button click.
     * @author walshj05
     */
    public void onAuction(ActionEvent actionEvent) {
        System.out.println(player.getName() + " Auction button clicked");
    }

    /**
     * Handles the action when the player clicks the "Trade" button.
     * @param actionEvent The action event triggered by the button click.
     * @author walshj05
     */
    public void onTrade(ActionEvent actionEvent) {
        System.out.println(player.getName() + " Trade button clicked");
    }

    /**
     * Handles the action when the player clicks the "Get Out of Jail" button.
     * @param actionEvent The action event triggered by the button click.
     * @author walshj05
     */
    public void onGetOutOfJail(ActionEvent actionEvent) {
        System.out.println(player.getName() + " Get Out of Jail button clicked");
    }

    /**
     * Handles the action when the player clicks the "Mortgage Property" button.
     * @param actionEvent The action event triggered by the button click.
     * @author walshj05
     */
    public void onMortgageProperty(ActionEvent actionEvent) {
        System.out.println(player.getName() + " Mortgage Property button clicked");
    }

    public void startTurn(){
        disableTurnButtons();
        rollDice.setDisable(false);
    }

    private void disableTurnButtons(){
        getOutOfJail.setDisable(true);
        drawCard.setDisable(true);
        buyProp.setDisable(true);
        buyHouse.setDisable(true);
        buyHotel.setDisable(true);
        endTurn.setDisable(true);
        rollDice.setDisable(true);
    }
}
