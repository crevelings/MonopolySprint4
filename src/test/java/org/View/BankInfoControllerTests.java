package org.View;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.monopoly.Model.Players.HumanPlayer;
import org.monopoly.Model.Players.Token;
import org.monopoly.View.GameScene.Board.BankInfoController;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the BankInfoController.
 *
 * @author shifmans
 */
class BankInfoControllerTests {

    private BankInfoController controller;
    private Label numHouses;
    private Label numHotels;
    private Label numProperties;

    /**
     * Developed by: shifmans
     */
    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {});
    }

    /**
     * Developed by: shifmans
     */
    @BeforeEach
    void setUp() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/org/monopoly/View/GameScene/Bank/BankInfo.fxml"));
        Parent root = loader.load(); // Load the FXML file
        controller = loader.getController(); // Retrieve the controller
        numHouses = (Label) root.lookup("#numHouses");
        numHotels = (Label) root.lookup("#numHotels");
        numProperties = (Label) root.lookup("#numProperties");

        assertNotNull(controller);
        assertNotNull(numHouses);
        assertNotNull(numHotels);
        assertNotNull(numProperties);
    }

    /**
     * Developed by: shifmans
     */
    @Test
    void testInitialize() {
        controller.initialize();

        assertEquals("32", numHouses.getText());
        assertEquals("12", numHotels.getText());
        assertEquals("28", numProperties.getText());
    }


    /**
     * Developed by: shifmans
     */
    // TODO: Change bank info when player buys/sells properties, houses, hotels
    /*
    @Test
    void testUpdateBankInfoAfterPurchase() {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        assertEquals(1500, humanPlayer.getBalance());

        humanPlayer.purchaseProperty("Park Place", 350);
        assertEquals(1150, humanPlayer.getBalance());
        assertTrue(humanPlayer.hasProperty("Park Place"));

        controller.updateBankInfo();

        assertEquals("27", numProperties.getText());
    }
     */
}