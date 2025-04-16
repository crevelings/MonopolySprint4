package org.monopoly.View.GameScene.Board;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * Controller for the Buy Pane in the Monopoly game.
 * This class handles the actions related to buying properties, houses, and hotels.
 * The following is reference material for implementation:
 * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/RadioButton.html">...</a>
 * @author walshj05
 */
public class BuyPaneController {
    @FXML
    Pane parentPane;
    @FXML
    RadioButton buyHouse;
    @FXML
    RadioButton buyHotel;
    @FXML
    RadioButton buyProperty;
    @FXML
    TextField propertyName;
    @FXML
    Button confirm;

    /**
     * Handles the action when the confirm button is pressed.
     * @param event The action event triggered by the button press.
     * @author walshj05
     */
    @FXML
    public void onConfirmButtonPress(ActionEvent event) {
        // Handle the action when the confirm button is pressed
        // Implement the logic for buying a house, hotel, or property here
    }
}
