package org.monopoly.View.GameScene.Board;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.monopoly.Model.Players.HumanPlayer;
import org.monopoly.Model.Players.Player;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Controller for the trade pane in the game.
 * This class handles the trade actions between players.
 * @author walshj05
 * Modified by: shifmans
 */
public class TradePaneController {
    @FXML
    private Label traderName;
    @FXML
    private TextField playerName;
    @FXML
    private TextField price;
    @FXML
    private ComboBox<String> item;
    @FXML
    private Button initiateTrade;
    private Player playerObject;
    private ArrayList<String> tradablePlayerNames;

    /**
     * Initializes the trade pane with the trader's name and available items.
     * @param trader The player initiating the trade.
     * @param allPlayers List of all players in the game.
     *
     * Developed by: shifmans
     */
    @FXML
    public void initialize(Player trader, ArrayList<Player> allPlayers) {
        ArrayList<String> playerItems = new ArrayList<>();
        playerItems.addAll(trader.getPropertiesOwned());
        playerItems.addAll(trader.getCards());

        playerObject = trader;
        traderName.setText(trader.getName());
        item.setItems(FXCollections.observableArrayList(playerItems));
        initiateTrade.setDisable(false);
        tradablePlayerNames = getTradablePlayerNames(allPlayers);
    }

    /**
     * Gets the names of players that can be traded with.
     * @param allPlayers List of all players in the game.
     * @return List of tradable player names.
     *
     * Developed by: shifmans
     */
    private ArrayList<String> getTradablePlayerNames(ArrayList<Player> allPlayers) {
        ArrayList<String> tradablePlayerNames = new ArrayList<>();

        for (Player player : allPlayers) {
            tradablePlayerNames.add(player.getName());
        }

        tradablePlayerNames.remove(playerObject.getName());

        return tradablePlayerNames;
    }

    /**
     * Handles the initiation of a trade.
     *
     * Developed by: shifmans
     */
    @FXML
    public void onInitiateTrade() {
        if (allFieldsFilled()) {
            displayTradeOptions();
        }
    }

    /**
     * Gets the type of item being traded.
     * @return Whether the item is a card or property.
     *
     * Developed by: shifmans
     */
    private String getItemType() {
        if (Objects.equals(item.getValue(), "Get out of Jail Free Card")) {
            return "card";
        }

        return "property";
    }

    /**
     * Checks if all fields are filled and valid.
     * @return Whether all fields are filled and valid.
     *
     * Developed by: shifmans
     */
    @FXML
    private boolean allFieldsFilled() {
        String playerField = playerName.getText();
        String itemField = item.getValue();
        String priceField = price.getText();

        if (playerField.isEmpty() || itemField == null || priceField.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Trade Offer");
            alert.showAndWait();

            return false;
        }

        else if (!tradablePlayerNames.contains(playerField)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Player Name");
            alert.showAndWait();

            return false;
        }

        return true;
    }

    /**
     * Displays a confirmation dialog for the trade offer.
     *
     * Developed by: shifmans
     */
    private void displayTradeOptions() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Trade Confirmation");
        alert.setContentText(playerName.getText() + ", do you want to accept this trade offer?");

        ButtonType acceptButton = new ButtonType("Accept");
        ButtonType declineButton = new ButtonType("Decline");
        alert.getButtonTypes().setAll(acceptButton, declineButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == acceptButton) {
                char answer = 'Y';

                String simulatedInput = playerName.getText() + "\n" + getItemType() + "\n" + item.getValue() + "\n" + price.getText() + "\n" + answer + "\n";
                System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
                ((HumanPlayer) playerObject).initiateTrade(playerObject);

            } else if (response == declineButton) {
                Alert declineAlert = new Alert(Alert.AlertType.INFORMATION);
                declineAlert.setTitle("Trade Declined");
                declineAlert.setHeaderText(null);
                declineAlert.setContentText("The trade was declined.");
                declineAlert.showAndWait();
            }
        });
    }
}