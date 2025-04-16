package org.monopoly.View.GameScene.Board;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * Controller for the auction pane in the game.
 * This class handles the auction actions and displays the auction information.
 * Note this class is still barebones, it will likely need to have instances of the players later on
 * @author walshj05
 */
public class AuctionPaneController {
    @FXML
    private Pane parentPane;
    @FXML
    private Button p1Bid;
    @FXML
    private Button p2Bid;
    @FXML
    private Button p3Bid;
    @FXML
    private Label p1Name;
    @FXML
    private Label p2Name;
    @FXML
    private Label p3Name;
    @FXML
    private Label p4Name;
    @FXML
    private Label playerAndHighestBid;
    @FXML
    private TextField p1BidValue;
    @FXML
    private TextField p2BidValue;
    @FXML
    private TextField p3BidValue;

    /**
     * Handles the action when the player 1 bid button is pressed.
     * This method is called when the player 1 bid button is clicked.
     * It updates the auction information and processes the bid.
     * @author walshj05
     */
    @FXML
    public void onP1BidButtonPress() {
        // Handle the action when the player 1 bid button is pressed
        // Implement the logic for player 1's bid
    }

    /**
     * Handles the action when the player 2 bid button is pressed.
     * This method is called when the player 2 bid button is clicked.
     * It updates the auction information and processes the bid.
     * @author walshj05
     */
    @FXML
    public void onP2BidButtonPress() {
        // Handle the action when the player 2 bid button is pressed
        // Implement the logic for player 2's bid
    }

    /**
     * Handles the action when the player 3 bid button is pressed.
     * This method is called when the player 3 bid button is clicked.
     * It updates the auction information and processes the bid.
     * @author walshj05
     */
    @FXML
    public void onP3BidButtonPress() {
        // Handle the action when the player 3 bid button is pressed
        // Implement the logic for player 3's bid
    }
}
