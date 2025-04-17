package org.monopoly.Model.Players;

import org.monopoly.Exceptions.*;
import org.monopoly.Model.*;

import org.monopoly.Model.Cards.ColorGroup;
import org.monopoly.Model.Cards.TitleDeedCards;
import org.monopoly.Model.GameTiles.PropertySpace;

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
        int[] rollResult = dice.roll();
        int die1 = rollResult[0];
        int die2 = rollResult[1];
        int total = die1 + die2;
        move(total);
    }

    /**
     * Player buys a property
     * @param property String
     * @throws InsufficientFundsException exception
     * @author walshj05
     */
    public void purchaseProperty(String property, int price) throws InsufficientFundsException {
        if (getBalance() >= price) {
            getPropertiesOwned().add(property);
            subtractFromBalance(price);
            updateMonopolies();
        } else {
            throw new InsufficientFundsException("Insufficient funds to purchase " + property);
        }
    }

    /**
     * Player sells a property
     * @param property String
     * @throws NoSuchPropertyException exception
     * @author walshj05
     */
    public void mortgageProperty(String property, int mortgageCost) throws NoSuchPropertyException {
        PropertySpace space = (PropertySpace) TitleDeedCards.getInstance().getProperty(property);
        if (getPropertiesOwned().contains(property)) {
            getPropertiesOwned().remove(property);
            getPropertiesMortgaged().add(property);
            addToBalance(mortgageCost);
        } else {
            throw new NoSuchPropertyException("You do not own " + property);
        }
    }

    /**
     * Player unmortgages a property
     * @param property String
     * @throws NoSuchPropertyException exception
     * @author crevelings
     */
    public void unmortgageProperty(String property, int mortgageValue) throws NoSuchPropertyException {
        if (getPropertiesMortgaged().contains(property)) {
            getPropertiesMortgaged().remove(property);
            getPropertiesOwned().add(property);
            subtractFromBalance(mortgageValue);
            // Deduct the mortgage value from the player's balance
            System.out.println(getName() + " unmortgaged " + property + " for $" + mortgageValue);
        } else {
            throw new NoSuchPropertyException("You do not have this property mortgaged.");
        }
    }

    /**
     * Player sells a property
     * @param property String
     * @throws NoSuchPropertyException exception
     * @author walshj05
     */
    public void sellProperty(String property, int propertyCost) throws NoSuchPropertyException {
        if (getPropertiesOwned().contains(property)) {
            getPropertiesOwned().remove(property);
            addToBalance(propertyCost);
            updateMonopolies();
        } else {
            throw new NoSuchPropertyException("You do not own " + property);
        }
    }

    /**
     * Subtracts a certain amount from the player's balance
     * @param amount int
     * @author walshj05
     */
    @Override
    public void subtractFromBalance(int amount) {
        if (getBalance() - amount < 0) {
            setBalance(0);
        } else {
            setBalance(getBalance() - amount);
        }
    }

    /**
     * Buys a hous on a certain property
     * @author walshj05
     */
    @Override
    public void buyHouse(String propertyName, ColorGroup colorGroup, int price) throws InsufficientFundsException, RuntimeException {
        if (getBalance() - price < 0) {
            throw new InsufficientFundsException("Insufficient funds to buy a house");
        }
        if (!getPropertiesOwned().contains(propertyName)) {
            throw new RuntimeException("Property not registered to player.");
        }

        int index = getColorGroups().indexOf(colorGroup);
        try {
            getMonopolies().get(index).buildHouse(propertyName);
        } catch (Exception e) {
            if (e.getMessage().equals("Index -1 out of bounds for length 0")) {
                throw new RuntimeException("Player does not have all properties in this monopoly.");
            }
            throw new RuntimeException(e.getMessage());
        }

        subtractFromBalance(price);
    }

    /**
     * Sells a house on a certain property
     * @author walshj05
     */
    @Override
    public void sellHouse(String propertyName, ColorGroup colorGroup) {
        if (!getPropertiesOwned().contains(propertyName) || !getColorGroups().contains(colorGroup)) {
            throw new RuntimeException("Property not registered to player.");
        }

        int index = getColorGroups().indexOf(colorGroup);
        try {
            getMonopolies().get(index).sellHouse(propertyName);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        TitleDeedCards cards = TitleDeedCards.getInstance();
        PropertySpace property = (PropertySpace) cards.getProperty(propertyName);
        addToBalance(property.getHousePrice()/2);
    }

    /**
     * Buys a hotel on a certain property
     * @author walshj05
     */
    @Override
    public void buyHotel(String propertyName, ColorGroup colorGroup, int price) throws InsufficientFundsException {
        if (getBalance() - price < 0) {
            throw new InsufficientFundsException("Insufficient funds to buy a hotel");
        }
        if (!getPropertiesOwned().contains(propertyName) || !getColorGroups().contains(colorGroup)) {
            throw new RuntimeException("Property not registered to player.");
        }

        int index = getColorGroups().indexOf(colorGroup);
        try {
            getMonopolies().get(index).buildHotel(propertyName);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        subtractFromBalance(price);
    }

    /**
     * Sells a hotel on a certain property
     * @author walshj05
     */
    @Override
    public void sellHotel(String propertyName, ColorGroup colorGroup) {
        if (!getPropertiesOwned().contains(propertyName) || !getColorGroups().contains(colorGroup)) {
            throw new RuntimeException("Property not registered to player.");
        }

        int index = getColorGroups().indexOf(colorGroup);
        try {
            getMonopolies().get(index).sellHotel(propertyName);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        TitleDeedCards cards = TitleDeedCards.getInstance();
        PropertySpace property = (PropertySpace) cards.getProperty(propertyName);
        addToBalance(property.getHotelPrice()/2);
    }
}
