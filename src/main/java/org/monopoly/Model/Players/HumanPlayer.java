package org.monopoly.Model.Players;

import org.monopoly.Model.*;

import org.monopoly.Model.Cards.ColorGroup;
import org.monopoly.Model.Cards.TitleDeedCards;
import org.monopoly.Model.GameTiles.PropertySpace;
import org.monopoly.View.GameScene.GameScene;

/**
 * A class representing a player in the Monopoly game.
 * More functionality will be added
 * @author walshj05, crevelings
 */
public class HumanPlayer extends Player {

    /**
     * Constructor for a HumanPlayer
     * @param name String
     * @param token Token
     * @author walshj05
     */
    public HumanPlayer(String name, Token token) {
        super(name, token);
    }


    /**
     * A method for a player to take a turn in the game
     * @param dice Dice object
     * @author walshj05
     */
    public void takeTurn (Dice dice) {
        rollDice(dice);
    }

    /**
     * When the player rolls the dice it will move their player.
     * @param dice
     */
    public void rollDice(Dice dice) {
        int[] rollResult = dice.roll();
        int die1 = rollResult[0];
        int die2 = rollResult[1];
        int total = die1 + die2;
        move(total);
    }

    /**
     * Player buys a property
     * @param property String
     * @author walshj05
     */
    public void purchaseProperty(String property, int price){
        if (getBalance() >= price) {
            getPropertiesOwned().add(property);
            subtractFromBalance(price);
            updateMonopolies();
        } else {
            GameScene.sendAlert("Insufficient funds to purchase " + property);
        }
    }

    /**
     * Player sells a property
     * @param property String
     * @author walshj05
     */
    public void mortgageProperty(String property, int mortgageCost){
        PropertySpace space = (PropertySpace) TitleDeedCards.getInstance().getProperty(property);
        if (getPropertiesOwned().contains(property)) {
            getPropertiesOwned().remove(property);
            getPropertiesMortgaged().add(property);
            addToBalance(mortgageCost);
        } else {
            GameScene.sendAlert("You do not own " + property);
        }
    }

    /**
     * Player unmortgages a property
     * @param property String
     * @author crevelings
     */
    public void unmortgageProperty(String property, int mortgageValue){
        if (getPropertiesMortgaged().contains(property)) {
            getPropertiesMortgaged().remove(property);
            getPropertiesOwned().add(property);
            subtractFromBalance(mortgageValue);
            // Deduct the mortgage value from the player's balance
        } else {
            GameScene.sendAlert("This property is not mortgaged: " + property);
        }
    }

    /**
     * Player sells a property
     * @param property String
     * @author walshj05
     */
    public void sellProperty(String property, int propertyCost){
        if (getPropertiesOwned().contains(property)) {
            getPropertiesOwned().remove(property);
            addToBalance(propertyCost);
            updateMonopolies();
        } else {
            GameScene.sendAlert("You do not own " + property);
        }
    }

    /**
     * Subtracts a certain amount from the player's balance
     * @param amount int
     * @author walshj05
     */
    @Override
    public void subtractFromBalance(int amount) {
        if ((getBalance() - amount) < 0) {
            // todo make it so player needs to sell stuff in order to pay
            setBalance(0);
        } else {
            setBalance(getBalance() - amount);
        }
    }

    /**
     * Buys a house on a certain property
     * @author walshj05
     */
    @Override
    public void buyHouse(String propertyName, ColorGroup colorGroup, int price){
        if (getBalance() - price < 0) {
            GameScene.sendAlert("Insufficient funds to buy a house");
            return;
        }
        if (!getPropertiesOwned().contains(propertyName) || !getColorGroups().contains(colorGroup)) {
            GameScene.sendAlert("Player does not have all properties in this monopoly.");
            return;
        }
        int index = getColorGroups().indexOf(colorGroup);
        int numHousesBefore = getNumHouses();
        getMonopolies().get(index).buildHouse(propertyName);
        if (numHousesBefore != getNumHouses())
            subtractFromBalance(price);
    }

    /**
     * Sells a house on a certain property
     * @author walshj05
     */
    @Override
    public void sellHouse(String propertyName, ColorGroup colorGroup) {
        if (!getPropertiesOwned().contains(propertyName) || !getColorGroups().contains(colorGroup)) {
            GameScene.sendAlert("Player does not have all properties in this monopoly.");
            return;
        }

        int index = getColorGroups().indexOf(colorGroup);
        TitleDeedCards cards = TitleDeedCards.getInstance();
        PropertySpace property = (PropertySpace) cards.getProperty(propertyName);
        int numHousesBefore = getNumHouses();
        getMonopolies().get(index).sellHouse(propertyName);
        if (numHousesBefore != getNumHouses())
            addToBalance(property.getHousePrice()/2);
    }

    /**
     * Buys a hotel on a certain property
     * @author walshj05
     */
    @Override
    public void buyHotel(String propertyName, ColorGroup colorGroup, int price){
        if (getBalance() - price < 0) {
            GameScene.sendAlert("Insufficient funds to buy a hotel.");
            return;
        }
        if (!getPropertiesOwned().contains(propertyName) || !getColorGroups().contains(colorGroup)) {
            GameScene.sendAlert("Player does not have all properties in this monopoly.");
            return;
        }
        int index = getColorGroups().indexOf(colorGroup);
        int numHotelsBefore = getNumHotels();
        getMonopolies().get(index).buildHotel(propertyName);
        if (numHotelsBefore != getNumHotels())
            subtractFromBalance(price);
    }

    /**
     * Sells a hotel on a certain property
     * @author walshj05
     */
    @Override
    public void sellHotel(String propertyName, ColorGroup colorGroup) {
        if (!getPropertiesOwned().contains(propertyName) || !getColorGroups().contains(colorGroup)) {
            GameScene.sendAlert("Player does not have all properties in this monopoly.");
            return;
        }

        int index = getColorGroups().indexOf(colorGroup);
        TitleDeedCards cards = TitleDeedCards.getInstance();
        PropertySpace property = (PropertySpace) cards.getProperty(propertyName);
        int numHotelsBefore = getNumHotels();
        getMonopolies().get(index).sellHotel(propertyName);
        if (numHotelsBefore != getNumHotels())
            addToBalance(property.getHotelPrice()/2);
    }
}
