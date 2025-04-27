package org.monopoly.Model.Players;

import org.monopoly.Exceptions.InsufficientFundsException;
import org.monopoly.Model.*;
import org.monopoly.Model.Cards.ColorGroup;
import org.monopoly.Model.Cards.TitleDeedCards;
import org.monopoly.Model.GameTiles.GameTile;
import org.monopoly.Model.GameTiles.PropertySpace;
import org.monopoly.View.ComputerPlayerController;

import java.util.*;

/**
 * A class that will represent a computer player in the game of Monopoly.
 * This class has a random number generator that will be used to determine the moves of the player.
 * @author walshj05, crevelings
 *
 */
public class ComputerPlayer extends Player {
    private final Random random;
    private ComputerPlayerController controller;
    /**
     * Constructor for a HumanPlayer
     *
     * @param name  String
     * @param token Token
     * @author walshj05
     */
    public ComputerPlayer(String name, Token token) {
        super(name, token);
        random = new Random(System.nanoTime());
    }

    public void setController(ComputerPlayerController controller) {
        this.controller = controller;
    }
    /**
     * A method for a player to take a turn in the game
     *
     * @param dice Dice object
     * @author walshj05
     */
    public void takeTurn(Dice dice) { //NOTE make all usages of dice as parameter singleton
        if (isInJail()) {
            jailTurnLogic();
            if (isInJail()) {
                endOfTurnProcess();
                return; // player is still in jail, so they cannot take a turn
            }
        }
        int numDoublesNeeded = 0;
        while(dice.getNumDoubles() == numDoublesNeeded){ // go until player no longer rolls a double
            int[] rollResult = dice.roll();
            int total = rollResult[0] + rollResult[1];
            if (dice.getNumDoubles() == 3){ // if player rolls three consecutive doubles, they go to jail
                goToJail();
                // end of turn process
            }
            move(total);
            numDoublesNeeded++;
            System.out.println(numDoublesNeeded);
            System.out.println(dice.isDouble());
        }
        endOfTurnProcess();
    }

    @Override
    public void addToBalance(int amount) {
        setBalance(getBalance() + amount);
        if (controller != null){// Check if controller is not null before updating
            controller.updateBalance();
        }
    }

    /**
     * Player buys a property
     *
     * @param property String
     * @throws InsufficientFundsException exception
     * @author walshj05
     *
     * Modified by: shifmans
     */
    public void purchaseProperty(String property, int price) throws InsufficientFundsException {
        boolean decision;

        if (getBalance() > 500) {
            decision = runOdds(0.85);
        } else {
            decision = runOdds(0.50);
        }

        if (decision) {
            if (getBalance() - price < 0) {
                throw new InsufficientFundsException("Insufficient funds to purchase " + property);
            }
            getPropertiesOwned().add(property);
            TitleDeedCards.getInstance().getProperty(property).setOwner(getName());
            subtractFromBalance(price);
            updateMonopolies();
            if (controller != null) {
                controller.updateProperties();
            }

        } else {
            // todo start auction process
            return;
        }
    }

    /**
     * Player mortgages a property
     *
     * @param propertyName String
     * @author crevelings
     * Modified by: shifmans
     */
    public void mortgageProperty(String propertyName, int mortgageCost) {
        getPropertiesOwned().remove(propertyName);
        getPropertiesMortgaged().add(propertyName);
        addToBalance(mortgageCost);
    }

    /**
     * Unmortgages a property for the player.
     * @param property The name of the property.
     * @param unmortgageValue The value to unmortgage the property.
     * @author crevelings
     * Modified by: shifmans
     */
    public void unmortgageProperty(String property, int unmortgageValue){
        getPropertiesMortgaged().remove(property);
        getPropertiesOwned().add(property);
        subtractFromBalance(unmortgageValue);// Deduct the mortgage value from the player's balance
    }

    /**
     * Player sells a property
     *
     * @param property String
     * @author crevelings
     * Modified by: shifmans
     * Modified by: walshj05 4/17/25
     */
    public void sellProperty(String property, int propertyCost) {
        getPropertiesOwned().remove(property);
        addToBalance(propertyCost);
        updateMonopolies();
        if (controller != null) {
            controller.updateProperties();
        }
    }

    /**
     * Subtracts a certain amount from the player's balance
     *
     * @param amount int
     * @author walshj05
     */
    @Override
    public void subtractFromBalance(int amount) {
        if (getBalance() < amount) {
            // add logic for selling something until you have enough money
            return;
        }
        setBalance(getBalance() - amount);
        if (controller != null){// Check if controller is not null before updating
            controller.updateBalance();
        }
    }

    /**
     * Modified by: shifmans
     * Modified by: walshj05
     */
    @Override
    public void buyHouse(String propertyName, ColorGroup colorGroup, int price){
        if ((getBalance() - price < 0) || !getPropertiesOwned().contains(propertyName)) {
            return;
        }
        int index = getColorGroups().indexOf(colorGroup);
        getMonopolies().get(index).buildHouse(propertyName);
        subtractFromBalance(price);
    }

    /**
     * Modified by: shifmans
     */
    @Override
    public void sellHouse(String propertyName, ColorGroup colorGroup) {
        int index = getColorGroups().indexOf(colorGroup);
        if (index == -1) {
            return;
        }
        try {
            getMonopolies().get(index).sellHouse(propertyName);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        TitleDeedCards cards = TitleDeedCards.getInstance();
        PropertySpace property = (PropertySpace) cards.getProperty(propertyName);
        addToBalance(property.getHotelPrice()/2);
    }

    /**
     * Modified by: shifmans
     */
    @Override
    public void buyHotel(String propertyName, ColorGroup colorGroup, int price){
        if ((getBalance() - price < 0) || !getPropertiesOwned().contains(propertyName)) {
            return;
        }
        int index = getColorGroups().indexOf(colorGroup);
        getMonopolies().get(index).buildHotel(propertyName);
        subtractFromBalance(price);
    }

    /**
     * Modified by: shifmans
     */
    @Override
    public void sellHotel(String propertyName, ColorGroup colorGroup) {
        int index = getColorGroups().indexOf(colorGroup);
        if (index == -1) {
            return;
        }
        try {
            getMonopolies().get(index).sellHotel(propertyName);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        TitleDeedCards cards = TitleDeedCards.getInstance();
        PropertySpace property = (PropertySpace) cards.getProperty(propertyName);
        addToBalance(property.getHotelPrice()/2);
    }


    /**
     * Allows the current player to take their turn
     * @author walshj05
     * Modified by: crevelings (4/1/25)
     * Modified by: walshj05
     */
    public void jailTurnLogic(){
        Dice dice = Dice.getInstance();
        if (getJailTurns() == 3){
            releaseFromJail();
        } else if (hasCard("community:Get Out of Jail Free")){ // use card
            removeCard("community:Get Out of Jail Free");
            GameBoard.getInstance().executeStrategyType(this, "community:Get Out of Jail Free");
        } else if (hasCard("chance:Get Out of Jail Free.")){ // use card
            removeCard("chance:Get Out of Jail Free.");
            GameBoard.getInstance().executeStrategyType(this, "chance:Get Out of Jail Free.");
        } else if (getBalance() > 250) { // pay to get out of jail
            subtractFromBalance(50);
            releaseFromJail();
        } else { // attempt to roll
            dice.roll();
            if (dice.isDouble()){
                releaseFromJail();
            } else {
                incrementJailTurns();
            }
        }
    }

    /**
     * Runs logic for selling and purchasing things based on current state of player
     * @author walshj05
     */
    public void endOfTurnProcess(){
        System.out.println("At end of turn");
        String propertyName = getRandomPropertyOwned();
        GameTile property = TitleDeedCards.getInstance().getProperty(propertyName);
        boolean hasAtLeastOneMonopoly = !getColorGroups().isEmpty();
        if (getPropertiesOwned().isEmpty()) {
            TurnManager.getInstance().nextPlayersTurn();
            return; // No properties owned
        }
        else if (getBalance() > 500 && runOdds(0.75)) {
            if (hasAtLeastOneMonopoly) {
                // tries to do one and then the other
                buyHouse(propertyName, property.getColorGroup(), property.getPrice());
                buyHotel(propertyName, property.getColorGroup(), property.getPrice());
            }
            if (!getPropertiesMortgaged().isEmpty()) {
                int unmortgageCost = TitleDeedCards.getInstance().getProperty(getPropertiesMortgaged().getFirst()).getUnmortgageValue();
                unmortgageProperty(getPropertiesMortgaged().getFirst(), unmortgageCost);
            }
        }
        else if (runOdds(0.50)){
            if (getNumHotels() + getNumHouses() == 0) {
                int value = property.getMortgageValue();
                mortgageProperty(propertyName, value);
            } else if (getNumHouses() > 0){ // sell house
                sellHouse(propertyName, property.getColorGroup());
            } else if (getNumHotels() > 0){ // sell hotel
                sellHotel(propertyName, property.getColorGroup());
            } else {//
                sellProperty(propertyName, property.getPrice());
            }
        }
        if (!TurnManager.getInstance().getPlayers().isEmpty()){
            TurnManager.getInstance().nextPlayersTurn();
        }
    }

    /**
     * Determines if the player will run a certain odd
     *
     * @param odd Likelihood of the event occurring
     * @return Whether the event will likely occur
     *
     * Developed by: shifmans
     */
    public boolean runOdds(double odd) {
        return random.nextDouble() <= odd;
    }

    /**
     * Gets a random property the player owns
     * @return String property name
     * @author walshj05
     */
    private String getRandomPropertyOwned(){
        List<String> properties = getPropertiesOwned();
        if (properties.isEmpty()) {
            return ""; // No properties owned
        }
        int randomIndex = random.nextInt(properties.size());
        return properties.get(randomIndex);
    }
}