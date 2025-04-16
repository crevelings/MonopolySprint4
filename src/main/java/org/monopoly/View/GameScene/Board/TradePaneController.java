package org.monopoly.View.GameScene.Board;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * Controller for the trade pane in the game.
 * This class handles the trade actions between players.
 * @author walshj05
 */
public class TradePaneController {
    @FXML
    Pane parentPane;
    @FXML
    private RadioButton p1property;
    @FXML
    private RadioButton p1money;
    @FXML
    private RadioButton p2property;
    @FXML
    private RadioButton p2money;
    @FXML
    private TextField p1TradeItem;
    @FXML
    private TextField p2TradeItem;
    @FXML
    private Button accept;
    @FXML
    private Button decline;

    /**
     * Handles the action when the decline button is pressed.
     * @param event The action event triggered by the button press.
     * @author walshj05
     */
    @FXML
    public void onAcceptButtonPress(ActionEvent event) {
        // Handle the accept button press
        // Implement the logic for accepting the trade
    }

    /**
     * Handles the action when the decline button is pressed.
     * @param event The action event triggered by the button press.
     * @author walshj05
     */
    @FXML
    public void onDeclineButtonPress(ActionEvent event) {
        // Handle the decline button press
        // Implement the logic for declining the trade
    }
}
