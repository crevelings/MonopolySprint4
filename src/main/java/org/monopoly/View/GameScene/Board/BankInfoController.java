package org.monopoly.View.GameScene.Board;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.monopoly.Model.Banker;

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
     * Updates the counts of houses, hotels, and properties owned by the banker.
     * @author shifmans, crevelings
     */
    public void updateCounts(int houses, int hotels, int properties) {
        numHouses.setText(String.valueOf(houses));
        numHotels.setText(String.valueOf(hotels));
        numProperties.setText(String.valueOf(properties));
    }
}
