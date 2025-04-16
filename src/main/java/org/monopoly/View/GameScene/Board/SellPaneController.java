package org.monopoly.View.GameScene.Board;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * Controller for the Sell Pane in the Monopoly game.
 * This class handles the actions related to selling properties, houses, and hotels, or to mortgaging properties.
 * The following is reference material for implementation:
 * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/RadioButton.html">...</a>
 * @author walshj05
 */
public class SellPaneController {
    @FXML
    Pane parentPane;
    @FXML
    private RadioButton sellHouse;
    @FXML
    private RadioButton sellHotel;
    @FXML
    private RadioButton sellProperty;
    @FXML
    private RadioButton mortgageProperty;
    @FXML
    private TextField property;
    @FXML
    private Button confirm;

    /**
     * Handles the action when the confirm button is pressed.
     * @param event The action event triggered by the button press.
     * @author walshj05
     */
    @FXML
    public void onConfirmButtonPress(ActionEvent event){

    }
}
