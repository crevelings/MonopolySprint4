package org.monopoly.View.GameScene.Board;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller class for the BankInfo view.
 * @author shifmans, crevelings
 */
public class BankInfoController {
    @FXML
    private Label numHouses;
    @FXML
    private Label numHotels;
    @FXML
    private Label numProperties;

    /**
     * Update the counts of houses, hotels, and properties in the BankInfo view.
     * @param houses Houses the banker has.
     * @param hotels Hotels the banker has.
     * @param properties Properties the banker has.
     *
     * Developed by: shifmans
     * Modified by: crevelings
     */
    public void updateCounts(int houses, int hotels, int properties) {
        numHouses.setText(String.valueOf(houses));
        numHotels.setText(String.valueOf(hotels));
        numProperties.setText(String.valueOf(properties));
    }
}
