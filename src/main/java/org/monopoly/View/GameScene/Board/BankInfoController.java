package org.monopoly.View.GameScene.Board;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.monopoly.Model.Banker;

/**
 * Controller class for the BankInfo view.
 * @author shifmans
 */
public class BankInfoController {
    @FXML
    private Label numHouses;
    @FXML
    private Label numHotels;
    @FXML
    private Label numProperties;

    /**
     * Initializes the BankInfo view with default values.
     *
     * Developed by: shifmans
     */
    @FXML
    public void initialize() {
        numHouses.setText("32");
        numHotels.setText("12");
        numProperties.setText("28");
    }

    /**
     * Updates the BankInfo view with the current values from the Banker instance.
     *
     * Developed by: shifmans
     */
    public void updateBankInfo() {
        Banker bank = Banker.getInstance();

        numHouses.setText(String.valueOf(bank.getHouses()));
        numHotels.setText(String.valueOf(bank.getHotels()));
        numProperties.setText(String.valueOf(bank.getDeck().getSize()));
    }
}
