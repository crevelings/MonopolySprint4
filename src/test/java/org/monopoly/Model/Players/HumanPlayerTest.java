package org.monopoly.Model.Players;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.monopoly.Exceptions.InsufficientFundsException;
import org.monopoly.Exceptions.NoSuchPropertyException;
import org.monopoly.Model.*;
import org.monopoly.Model.Cards.ColorGroup;
import org.monopoly.Model.Cards.TitleDeedCards;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the HumanPlayer class.
 *
 * @author walshj05
 */
public class HumanPlayerTest {

    /**
     * Developed by: shifmans
     */
    @BeforeEach
    public void resetSingletons() {
        Banker.resetInstance();
        TitleDeedCards.resetInstance();
        GameBoard.resetInstance();
    }

    @Test
    public void testPlayerCreation() {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        assertEquals("John Doe", humanPlayer.getName());
        assertEquals(1500, humanPlayer.getBalance());
        assertEquals(0, humanPlayer.getPosition());
    }

    @Test
    public void testPlayerBalance() {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        humanPlayer.addToBalance(2000);
        assertEquals(3500, humanPlayer.getBalance());
    }

    @Test
    public void testPlayerName() {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        assertEquals("John Doe", humanPlayer.getName());
    }

    @Test
    public void testPlayerSetPosition() {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        humanPlayer.setPosition(5);
        assertEquals(5, humanPlayer.getPosition());
    }

    @Test
    void playerMoveWorksWhenNotInJail() {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        humanPlayer.move(5);
        assertEquals(5, humanPlayer.getPosition());
    }

    @Test
    void playerDoesNotMoveWhenInJail() {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        humanPlayer.goToJail(); // new pos in jail (10)
        assertEquals(10, humanPlayer.getPosition());
        humanPlayer.move(5);
        assertEquals(10, humanPlayer.getPosition());
    }

    @Test
    void playerCanGetOutOfJail() {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        humanPlayer.goToJail(); // new pos in jail (10)
        assertEquals(10, humanPlayer.getPosition());
        humanPlayer.releaseFromJail();
        assertFalse(humanPlayer.isInJail());
        humanPlayer.move(5);
        assertEquals(15, humanPlayer.getPosition());
    }

    @Test
    void playerHasAGameToken () {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        assertNotNull(humanPlayer.getToken());
    }

    @Test
    void playerCanPurchaseProperty() throws InsufficientFundsException {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        humanPlayer.purchaseProperty("Park Place", 350);
        assertTrue(humanPlayer.hasProperty("Park Place"));
        assertEquals(1150, humanPlayer.getBalance());
    }

    @Test
    void playerCannotPurchasePropertyIfInsufficientFunds() {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        humanPlayer.subtractFromBalance(1400); // Set balance to 100
        humanPlayer.purchaseProperty("Park Place", 350);
        assertFalse(humanPlayer.hasProperty("Park Place"));
    }

    @Test
    void playerTaxedForMoreThanTheyCanAffordGoesBankrupt(){
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        humanPlayer.subtractFromBalance(1400); // Set balance to 100
        humanPlayer.subtractFromBalance(2000); // Taxed 2000
        assertTrue(humanPlayer.isBankrupt());
    }

    @Test
    void playerCanAddAndRemoveCardsFromTheirHand(){
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        humanPlayer.addCard("Get Out of Jail Free");
        assertTrue(humanPlayer.hasCard("Get Out of Jail Free"));
        humanPlayer.removeCard("Get Out of Jail Free");
        assertFalse(humanPlayer.hasCard("Get Out of Jail Free"));
    }

    @Test
    void playerToStringMethodWorks(){
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        assertEquals("John Doe (Token: John Doe)", humanPlayer.toString());
    }

    @Test
    void testPlayerCanSellProperty() throws InsufficientFundsException, NoSuchPropertyException {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        humanPlayer.purchaseProperty("Park Place", 350);
        humanPlayer.sellProperty("Park Place", 350);
        assertFalse(humanPlayer.hasProperty("Park Place"));
        assertEquals(1500, humanPlayer.getBalance());
    }

    @Test
    void mortgagePropertyWorks() throws InsufficientFundsException, NoSuchPropertyException {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        humanPlayer.purchaseProperty("Park Place", 350);
        humanPlayer.mortgageProperty("Park Place", 175);
        assertFalse(humanPlayer.hasProperty("Park Place"));
        assertEquals(1325, humanPlayer.getBalance());
    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testPlayerUnmortgageProperty() throws InsufficientFundsException, NoSuchPropertyException {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        assertEquals(1500, humanPlayer.getBalance());
        assertEquals(List.of(), humanPlayer.getPropertiesOwned());
        assertEquals(List.of(), humanPlayer.getPropertiesMortgaged());

        humanPlayer.purchaseProperty("Park Place", 100);
        assertEquals(1400, humanPlayer.getBalance());
        assertEquals(1, humanPlayer.getPropertiesOwned().size());
        assertEquals(List.of("Park Place"), humanPlayer.getPropertiesOwned());

        humanPlayer.mortgageProperty("Park Place", 50);
        assertEquals(1450, humanPlayer.getBalance());
        assertEquals(0, humanPlayer.getPropertiesOwned().size());
        assertEquals(List.of("Park Place"), humanPlayer.getPropertiesMortgaged());

        humanPlayer.unmortgageProperty("Park Place", 50);
        assertEquals(1400, humanPlayer.getBalance());
        assertEquals(List.of("Park Place"), humanPlayer.getPropertiesOwned());
        assertEquals(0, humanPlayer.getPropertiesMortgaged().size());
    }

    @Test
    void testPlayerInJailCannotMove(){
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        humanPlayer.goToJail();
        assertEquals(10, humanPlayer.getPosition());
        humanPlayer.takeTurn(new Dice());
        assertEquals(10, humanPlayer.getPosition());
    }

    @Test
    void playerCanBeInJailForMultipleTurns(){
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        humanPlayer.goToJail();
        assertEquals(10, humanPlayer.getPosition());
        humanPlayer.takeTurn(new Dice());
        humanPlayer.incrementJailTurns();
        assertEquals(10, humanPlayer.getPosition());
        humanPlayer.incrementJailTurns();
        assertEquals(10, humanPlayer.getPosition());
        assertEquals(2, humanPlayer.getJailTurns());
        humanPlayer.resetJailTurns();
        assertEquals(0, humanPlayer.getJailTurns());
    }

    /*
    Add a test for hasMonopoly
     */
    @Test
    void testPlayerHasMonopoly() throws InsufficientFundsException {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        humanPlayer.purchaseProperty("Park Place", 350);
        humanPlayer.purchaseProperty("Boardwalk", 400);
        assertTrue(humanPlayer.hasMonopoly(ColorGroup.DARK_BLUE));
    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testPlayerBuyOneHouseNoMonopoly() {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        assertEquals(1500, humanPlayer.getBalance());

        humanPlayer.purchaseProperty("Park Place", 350);
        assertEquals(1150, humanPlayer.getBalance());
        assertTrue(humanPlayer.hasProperty("Park Place"));
        assertFalse(humanPlayer.hasMonopoly(ColorGroup.DARK_BLUE));
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        assertEquals(1150, humanPlayer.getBalance());
    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testPlayerBuyOneHouseNoMoney(){
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        assertEquals(1500, humanPlayer.getBalance());

        humanPlayer.purchaseProperty("Park Place", 350);
        assertEquals(1150, humanPlayer.getBalance());

        // Take player money for testing purposes
        humanPlayer.subtractFromBalance(1000);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        assertEquals(150, humanPlayer.getBalance());
    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testPlayerBuyHouseUnownedProperty(){
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        assertEquals(1500, humanPlayer.getBalance());
        humanPlayer.purchaseProperty("Park Place", 350);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        assertEquals(0, humanPlayer.getNumHouses());

        assertEquals(1150, humanPlayer.getBalance());
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        assertEquals(0, humanPlayer.getNumHouses());
    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testPlayerBuyOneHouse() throws InsufficientFundsException {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        assertEquals(1500, humanPlayer.getBalance());

        humanPlayer.purchaseProperty("Park Place", 350);
        assertEquals(1150, humanPlayer.getBalance());
        assertTrue(humanPlayer.hasProperty("Park Place"));
        assertEquals(List.of("Park Place"), humanPlayer.getPropertiesOwned());
        assertFalse(humanPlayer.hasMonopoly(ColorGroup.DARK_BLUE));
        assertEquals(0, humanPlayer.getMonopolies().size());

        humanPlayer.purchaseProperty("Boardwalk", 400);
        assertEquals(750, humanPlayer.getBalance());
        assertTrue(humanPlayer.hasProperty("Boardwalk"));
        assertEquals(Arrays.asList("Park Place", "Boardwalk"), humanPlayer.getPropertiesOwned());
        assertTrue(humanPlayer.hasMonopoly(ColorGroup.DARK_BLUE));
        assertEquals(1, humanPlayer.getMonopolies().size());

        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        assertEquals(550, humanPlayer.getBalance());
    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testCheckEvenBuildRule() throws InsufficientFundsException {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        assertEquals(1500, humanPlayer.getBalance());

        humanPlayer.purchaseProperty("Park Place", 350);
        assertEquals(1150, humanPlayer.getBalance());
        assertTrue(humanPlayer.hasProperty("Park Place"));
        assertEquals(List.of("Park Place"), humanPlayer.getPropertiesOwned());
        assertFalse(humanPlayer.hasMonopoly(ColorGroup.DARK_BLUE));
        assertEquals(0, humanPlayer.getMonopolies().size());

        humanPlayer.purchaseProperty("Boardwalk", 400);
        assertEquals(750, humanPlayer.getBalance());
        assertTrue(humanPlayer.hasProperty("Boardwalk"));
        assertEquals(Arrays.asList("Park Place", "Boardwalk"), humanPlayer.getPropertiesOwned());
        assertTrue(humanPlayer.hasMonopoly(ColorGroup.DARK_BLUE));
        assertEquals(1, humanPlayer.getMonopolies().size());

        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        assertEquals(550, humanPlayer.getBalance());

        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        assertEquals(550, humanPlayer.getBalance());
    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testPlayerOverBuyHouses() throws InsufficientFundsException {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        assertEquals(1500, humanPlayer.getBalance());

        // Give player money for testing purposes
        humanPlayer.addToBalance(10000);
        assertEquals(11500, humanPlayer.getBalance());

        humanPlayer.purchaseProperty("Park Place", 350);
        humanPlayer.purchaseProperty("Boardwalk", 400);
        humanPlayer.hasMonopoly(ColorGroup.DARK_BLUE);
        assertEquals(10750, humanPlayer.getBalance());

        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        assertEquals(9150, humanPlayer.getBalance());

        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        assertEquals(9150, humanPlayer.getBalance());
    }

    /**
     * Developed by: shifmans
     */
    @Test void testPlayerSellOneHouse() throws InsufficientFundsException {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        assertEquals(1500, humanPlayer.getBalance());

        humanPlayer.purchaseProperty("Park Place", 350);
        assertTrue(humanPlayer.hasProperty("Park Place"));
        assertFalse(humanPlayer.hasMonopoly(ColorGroup.DARK_BLUE));

        humanPlayer.purchaseProperty("Boardwalk", 400);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        assertEquals(550, humanPlayer.getBalance());

        humanPlayer.sellHouse("Park Place", ColorGroup.DARK_BLUE);
        assertEquals(650, humanPlayer.getBalance());
    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testCheckEvenSellRule() throws InsufficientFundsException {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        assertEquals(1500, humanPlayer.getBalance());

        humanPlayer.purchaseProperty("Park Place", 350);
        assertTrue(humanPlayer.hasProperty("Park Place"));
        assertFalse(humanPlayer.hasMonopoly(ColorGroup.DARK_BLUE));

        humanPlayer.purchaseProperty("Boardwalk", 400);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        assertEquals(550, humanPlayer.getBalance());

        humanPlayer.sellHouse("Park Place", ColorGroup.DARK_BLUE);
        assertEquals(650, humanPlayer.getBalance());

        humanPlayer.sellHouse("Park Place", ColorGroup.DARK_BLUE);
        assertEquals(650, humanPlayer.getBalance());
    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testPlayerOverSellHouses() throws InsufficientFundsException {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        assertEquals(1500, humanPlayer.getBalance());

        // Give player money for testing purposes
        humanPlayer.addToBalance(10000);
        assertEquals(11500, humanPlayer.getBalance());

        humanPlayer.purchaseProperty("Park Place", 350);
        humanPlayer.purchaseProperty("Boardwalk", 400);
        humanPlayer.hasMonopoly(ColorGroup.DARK_BLUE);
        assertEquals(10750, humanPlayer.getBalance());

        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        assertEquals(9150, humanPlayer.getBalance());

        humanPlayer.sellHouse("Park Place", ColorGroup.DARK_BLUE);
        humanPlayer.sellHouse("Boardwalk", ColorGroup.DARK_BLUE);
        humanPlayer.sellHouse("Park Place", ColorGroup.DARK_BLUE);
        humanPlayer.sellHouse("Boardwalk", ColorGroup.DARK_BLUE);
        humanPlayer.sellHouse("Park Place", ColorGroup.DARK_BLUE);
        humanPlayer.sellHouse("Boardwalk", ColorGroup.DARK_BLUE);
        humanPlayer.sellHouse("Park Place", ColorGroup.DARK_BLUE);
        humanPlayer.sellHouse("Boardwalk", ColorGroup.DARK_BLUE);

        assertEquals(9950, humanPlayer.getBalance());
    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testPlayerSellHouseUnownedProperty() throws InsufficientFundsException {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        assertEquals(List.of(), humanPlayer.getPropertiesOwned());
        humanPlayer.sellHouse("Park Place", ColorGroup.DARK_BLUE);
        assertEquals(List.of(), humanPlayer.getPropertiesOwned());

        humanPlayer.purchaseProperty("Park Place", 350);
        assertEquals(1150, humanPlayer.getBalance());
        humanPlayer.sellHouse("Boardwalk", ColorGroup.DARK_BLUE);
        assertEquals(1150, humanPlayer.getBalance());
    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testPlayerBuyHotel() throws InsufficientFundsException {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        assertEquals(1500, humanPlayer.getBalance());

        // Give player money for testing purposes
        humanPlayer.addToBalance(10000);
        assertEquals(11500, humanPlayer.getBalance());

        humanPlayer.purchaseProperty("Park Place", 350);
        humanPlayer.purchaseProperty("Boardwalk", 400);
        humanPlayer.hasMonopoly(ColorGroup.DARK_BLUE);
        assertEquals(10750, humanPlayer.getBalance());

        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        assertEquals(9150, humanPlayer.getBalance());

        humanPlayer.buyHotel("Park Place", ColorGroup.DARK_BLUE, 200);
        assertEquals(8950, humanPlayer.getBalance());
    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testPlayerBuyHotelNoMoney() throws InsufficientFundsException {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        assertEquals(1500, humanPlayer.getBalance());

        // Give player money for testing purposes
        humanPlayer.addToBalance(900);
        assertEquals(2400, humanPlayer.getBalance());

        humanPlayer.purchaseProperty("Park Place", 350);
        humanPlayer.purchaseProperty("Boardwalk", 400);
        humanPlayer.hasMonopoly(ColorGroup.DARK_BLUE);
        assertEquals(1650, humanPlayer.getBalance());

        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        assertEquals(50, humanPlayer.getBalance());

        humanPlayer.buyHotel("Park Place", ColorGroup.DARK_BLUE, 200);
        assertEquals(50, humanPlayer.getBalance());
    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testPlayerBuyHotelUnownedProperty() throws InsufficientFundsException {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        assertEquals(1500, humanPlayer.getBalance());

        // Give player money for testing purposes
        humanPlayer.addToBalance(1500);
        assertEquals(3000, humanPlayer.getBalance());

        humanPlayer.purchaseProperty("Park Place", 350);
        humanPlayer.purchaseProperty("Boardwalk", 400);
        humanPlayer.hasMonopoly(ColorGroup.DARK_BLUE);
        assertEquals(2250, humanPlayer.getBalance());

        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        assertEquals(650, humanPlayer.getBalance());

        humanPlayer.buyHotel("Short Line Railroad", ColorGroup.DARK_BLUE, 200);
        assertEquals(650, humanPlayer.getBalance());
    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testPlayerSellHotel() throws InsufficientFundsException {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        assertEquals(1500, humanPlayer.getBalance());

        // Give player money for testing purposes
        humanPlayer.addToBalance(10000);
        assertEquals(11500, humanPlayer.getBalance());

        humanPlayer.purchaseProperty("Park Place", 350);
        humanPlayer.purchaseProperty("Boardwalk", 400);
        humanPlayer.hasMonopoly(ColorGroup.DARK_BLUE);
        assertEquals(10750, humanPlayer.getBalance());

        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        assertEquals(9150, humanPlayer.getBalance());
        assertEquals(8, humanPlayer.getNumHouses());
        assertEquals(0, humanPlayer.getNumHotels());

        humanPlayer.buyHotel("Park Place", ColorGroup.DARK_BLUE, 200);
        assertEquals(8950, humanPlayer.getBalance());
        assertEquals(1, humanPlayer.getNumHotels());

        humanPlayer.sellHotel("Park Place", ColorGroup.DARK_BLUE);
        assertEquals(9050, humanPlayer.getBalance());
        assertEquals(0, humanPlayer.getNumHotels());
    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testPlayerSellHotelUnownedProperty() throws InsufficientFundsException {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        assertEquals(1500, humanPlayer.getBalance());

        // Give player money for testing purposes
        humanPlayer.addToBalance(10000);
        assertEquals(11500, humanPlayer.getBalance());

        humanPlayer.purchaseProperty("Park Place", 350);
        humanPlayer.purchaseProperty("Boardwalk", 400);
        humanPlayer.hasMonopoly(ColorGroup.DARK_BLUE);
        assertEquals(10750, humanPlayer.getBalance());

        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
        assertEquals(9150, humanPlayer.getBalance());

        humanPlayer.buyHotel("Park Place", ColorGroup.DARK_BLUE, 200);
        assertEquals(8950, humanPlayer.getBalance());

        humanPlayer.sellHotel("Short Line Railroad", ColorGroup.DARK_BLUE);
        assertEquals(8950, humanPlayer.getBalance());
    }

    /**
     * Test to see if the player's position rolls over once it reaches 39
     */
    @Test
    public void testPlayerPositionRollOver() {
        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
        humanPlayer.setPosition(39);
        humanPlayer.move(2);
        assertEquals(1, humanPlayer.getPosition());

        humanPlayer.setPosition(38);
        humanPlayer.move(10);
        assertEquals(8, humanPlayer.getPosition());
    }

//    @Test
//    public void testMortgageAssetsRaisesFundsSuccessfully() throws InsufficientFundsException, BankruptcyException {
//        Token token = new Token("Hat", "TokensPNGs/Hat.png");
//        HumanPlayer player = new HumanPlayer("Test Player", token);
//
//        // Set up a property
//        PropertySpace prop = new PropertySpace(
//                "Baltic Avenue", "", 60,
//                new ArrayList<>(List.of(4, 20, 60, 180, 320, 450)),
//                ColorGroup.BROWN, 50, 50, 30);
//
//        player.purchaseProperty(prop.getName(), 60);
//        player.subtractFromBalance(1450);
//
//        // Try to raise $30
//        player.mortgageAssetsToRaiseFunds(30);
//
//        // Assert balance increased
//        assertTrue(player.getBalance() >= 30);
//
//        // Assert property is mortgaged
//        assertEquals(0, player.getPropertiesOwned().size());
//    }
//
//    /**
//     * Developed by: shifmans
//     */
//    @Test
//    public void testMortgageAssetsToRaiseFunds() throws InsufficientFundsException, BankruptcyException {
//        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
//        assertEquals(1500, humanPlayer.getBalance());
//
//        humanPlayer.purchaseProperty("Park Place", 350);
//        assertEquals(1150, humanPlayer.getBalance());
//        assertEquals(List.of(), humanPlayer.getPropertiesMortgaged());
//        assertEquals(List.of("Park Place"), humanPlayer.getPropertiesOwned());
//
//        humanPlayer.mortgageAssetsToRaiseFunds(100);
//        // Mortgage value is 175
//        assertEquals(1325, humanPlayer.getBalance());
//        assertEquals(List.of("Park Place"), humanPlayer.getPropertiesMortgaged());
//        assertEquals(List.of(), humanPlayer.getPropertiesOwned());
//    }
//
//    /**
//     * Developed by: shifmans
//     */
//    @Test
//    public void testSellBuildingsToRaiseFundsFail() throws InsufficientFundsException, BankruptcyException {
//        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token("John Doe", "BattleShip.png"));
//        assertEquals(1500, humanPlayer.getBalance());
//
//        humanPlayer.purchaseProperty("Park Place", 350);
//        humanPlayer.purchaseProperty("Boardwalk", 400);
//        humanPlayer.hasMonopoly(ColorGroup.DARK_BLUE);
//        assertEquals(750, humanPlayer.getBalance());
//
//        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
//        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
//        assertEquals(350, humanPlayer.getBalance());
//        assertEquals(2, humanPlayer.getNumHouses());
//
//        assertThrows(BankruptcyException.class, () -> humanPlayer.sellBuildingsToRaiseFunds(1000));
//    }
//
//    /**
//     * Developed by: shifmans
//     */
//    @Test
//    public void testAttemptToRaiseFunds() throws InsufficientFundsException, BankruptcyException {
//        HumanPlayer humanPlayer = new HumanPlayer("John Doe", new Token( "John Doe","BattleShip.png"));
//        assertEquals(1500, humanPlayer.getBalance());
//
//        humanPlayer.purchaseProperty("Park Place", 350);
//        assertEquals(1150, humanPlayer.getBalance());
//        assertEquals(List.of(), humanPlayer.getPropertiesMortgaged());
//        assertEquals(List.of("Park Place"), humanPlayer.getPropertiesOwned());
//
//        humanPlayer.attemptToRaiseFunds(100);
//        // Mortgage value is 175
//        assertEquals(1325, humanPlayer.getBalance());
//        assertEquals(List.of("Park Place"), humanPlayer.getPropertiesMortgaged());
//        assertEquals(List.of(), humanPlayer.getPropertiesOwned());
//
//        humanPlayer.addToBalance(175);
//        assertEquals(1500, humanPlayer.getBalance());
//
//        humanPlayer.purchaseProperty("Park Place", 350);
//        humanPlayer.purchaseProperty("Boardwalk", 400);
//        humanPlayer.hasMonopoly(ColorGroup.DARK_BLUE);
//        assertEquals(750, humanPlayer.getBalance());
//
//        humanPlayer.buyHouse("Park Place", ColorGroup.DARK_BLUE, 200);
//        humanPlayer.buyHouse("Boardwalk", ColorGroup.DARK_BLUE, 200);
//        assertEquals(350, humanPlayer.getBalance());
//        assertEquals(2, humanPlayer.getNumHouses());
//
//        assertThrows(BankruptcyException.class, () -> humanPlayer.sellBuildingsToRaiseFunds(1000));
//    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testQuitGame() {
        Token[] tokens = {new Token("Player 1", "BattleShip.png"), new Token("Player 2", "Car.png"), new Token("CPU 1", "Hat.png")};
        ArrayList<Player> human = new ArrayList<>();
        human.add(new HumanPlayer("Player 1", tokens[0]));
        human.add(new HumanPlayer("Player 2", tokens[1]));
        ArrayList<Player> computer = new ArrayList<>();
        computer.add(new ComputerPlayer("CPU 1", tokens[2]));
        Game game = new Game(human, computer);
        TurnManager tm = TurnManager.getInstance();

        HumanPlayer player = (HumanPlayer) tm.getPlayers().get(0);
        player.quitGame(player);

        assertEquals(2, tm.getPlayers().size());
        assertFalse(tm.getPlayers().contains(player));
    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testEndGameSuccessful() {
        Token[] tokens = {new Token("Player 1", "BattleShip.png"), new Token("Player 2", "Car.png"), new Token("CPU 1", "Hat.png")};
        ArrayList<Player> human = new ArrayList<>();
        human.add(new HumanPlayer("Player 1", tokens[0]));
        human.add(new HumanPlayer("Player 2", tokens[1]));
        ArrayList<Player> computer = new ArrayList<>();
        computer.add(new ComputerPlayer("CPU 1", tokens[2]));
        Game game = new Game(human, computer);
        TurnManager tm = TurnManager.getInstance();

        assertEquals(3, tm.getPlayers().size());

        tm.getPlayers().get(0).subtractFromBalance(500);
        tm.getPlayers().get(1).subtractFromBalance(300);
        tm.getPlayers().get(2).subtractFromBalance(600);

        assertEquals(1000, tm.getPlayers().get(0).getBalance());
        assertEquals(1200, tm.getPlayers().get(1).getBalance());
        assertEquals(900, tm.getPlayers().get(2).getBalance());

        String simulatedInput = "Y\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        HumanPlayer player = (HumanPlayer) tm.getPlayers().get(0);
        player.endGame();

        assertEquals(0, tm.getPlayers().size());
    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testEndGameFail() {
        Token[] tokens = {new Token("Player 1", "BattleShip.png"), new Token("Player 2", "Car.png"), new Token("CPU 1", "Hat.png")};
        ArrayList<Player> human = new ArrayList<>();
        human.add(new HumanPlayer("Player 1", tokens[0]));
        human.add(new HumanPlayer("Player 2", tokens[1]));
        ArrayList<Player> computer = new ArrayList<>();
        computer.add(new ComputerPlayer("CPU 1", tokens[2]));
        Game game = new Game(human, computer);
        TurnManager tm = TurnManager.getInstance();

        assertEquals(3, tm.getPlayers().size());

        String simulatedInput = "N\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        HumanPlayer player = (HumanPlayer) tm.getPlayers().get(0);
        player.endGame();

        assertEquals(3, tm.getPlayers().size());
    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testTradeProperty() {
        Token[] tokens = {new Token("Player 1", "BattleShip.png"), new Token("Player 2", "Car.png")};
        ArrayList<Player> human = new ArrayList<>();
        human.add(new HumanPlayer("Player 1", tokens[0]));
        human.add(new HumanPlayer("Player 2", tokens[1]));
        ArrayList<Player> computer = new ArrayList<>();
        Game game = new Game(human, computer);
        TurnManager tm = TurnManager.getInstance();

        HumanPlayer trader = (HumanPlayer) tm.getPlayers().get(0);
        HumanPlayer responder = (HumanPlayer) tm.getPlayers().get(1);

        String simulatedInput = "Player 2\nproperty\nPark Place\n200\nY\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        trader.purchaseProperty("Park Place", 350);
        assertTrue(trader.hasProperty("Park Place"));
        assertEquals(1150, trader.getBalance());
        assertEquals(1500, responder.getBalance());

        trader.initiateTrade(trader);
        assertFalse(trader.hasProperty("Park Place"));
        assertTrue(responder.hasProperty("Park Place"));
        assertEquals(1350, trader.getBalance());
        assertEquals(1300, responder.getBalance());
    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testTradeCard() {
        Token[] tokens = {new Token("Player 1", "BattleShip.png"), new Token("Player 2", "Car.png")};
        ArrayList<Player> human = new ArrayList<>();
        human.add(new HumanPlayer("Player 1", tokens[0]));
        human.add(new HumanPlayer("Player 2", tokens[1]));
        ArrayList<Player> computer = new ArrayList<>();
        Game game = new Game(human, computer);
        TurnManager tm = TurnManager.getInstance();

        HumanPlayer trader = (HumanPlayer) tm.getPlayers().get(0);
        HumanPlayer responder = (HumanPlayer) tm.getPlayers().get(1);

        String simulatedInput = "Player 2\ncard\n200\nY\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        assertEquals(0, trader.getCards().size());
        trader.addCard("Get Out of Jail Free.");
        assertEquals(1, trader.getCards().size());

        trader.initiateTrade(trader);
        assertFalse(trader.hasCard("Get Out of Jail Free."));
        assertTrue(responder.hasCard("Get Out of Jail Free."));
        assertEquals(1700, trader.getBalance());
        assertEquals(1300, responder.getBalance());
    }
}
