package org.monopoly.Model.Players;

import org.monopoly.Exceptions.*;
import org.monopoly.Model.*;
import org.monopoly.Model.Cards.ColorGroup;
import org.monopoly.View.GUI;
import org.monopoly.View.GameScene.GameScene;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Abstract class for the different types of players in the Monopoly game.
 * @author walshj05
 * Modified by: crevelings (4/9/25)
 * 4/9/25: Added methods to match up both player inheritors
 */
public abstract class Player {
    private final String name;
    private int position;
    private int balance;
    private boolean inJail;
    private final Token token;
    private final ArrayList<String> propertiesOwned;
    private final ArrayList<String> propertiesMortgaged;
    private final ArrayList<Monopoly> monopolies;
    private final ArrayList<ColorGroup> colorGroups;
    private final ArrayList<String> cards;
    private int jailTurns;

    /**
     * Constructor for the Player class.
     * @param name the name of the player
     * @param token the token of the player
     */
    public Player(String name, Token token) {
        this.name = name;
        this.token = token;
        this.position = 0;
        this.balance = 1500;
        this.inJail = false;
        this.propertiesOwned = new ArrayList<>();
        this.propertiesMortgaged = new ArrayList<>();
        this.monopolies = new ArrayList<>();
        this.colorGroups = new ArrayList<>();
        this.cards = new ArrayList<>();
        this.jailTurns = 0;
        GameBoard.getInstance().addToken(token, position);
    }

    /**
     * Getters and Setters
     * @author walshj05
     */
    public String getName(){
        return name;
    }

    public Token getToken() {
        return token;
    }

    public int getPosition() {
        return position;
    }

    public int getBalance() {
        return balance;
    }

    public void setPosition(int position) {
        GameBoard.getInstance().removeToken(this.token, this.position); // Remove token from old position
        this.position = position; // Move the player
        GameBoard.getInstance().addToken(this.token, this.position);
        GameBoard.getInstance().executeStrategyType(this, "tile");
    }
    public void setBalance(int balance){
        this.balance = balance;
    }

    /**
     * Gets the properties owned by the player.
     *
     * Developed by: shifmans
     */
    public ArrayList<String> getPropertiesOwned() {
        return propertiesOwned;
    }

    /**
     * Gets the properties that are mortgaged.
     * @return Properties that are mortgaged.
     *
     * Developed by: shifmans
     */
    public ArrayList<String> getPropertiesMortgaged() {
        return propertiesMortgaged;
    }

    /**
     * Gets the monopolies a player has.
     * @return Monopolies a player has.
     *
     * Developed by: shifmans
     */
    public ArrayList<Monopoly> getMonopolies() {
        return monopolies;
    }



    /**
     * Moves player a certain number of spaces
     * Also checks if they are in jail or not
     * @param spaces num spaces moved
     * @author walshj05
     */
    public void move(int spaces) {
        if (!inJail) {
            GameBoard.getInstance().removeToken(this.token, position); // Remove token from old position
            position += spaces; // Move the player
            if (position > 40){
                this.addToBalance(200);
                GameScene.sendAlert("passed go!!!"); //todo REMOVE ME
            }
            position %= 40;
            GameBoard.getInstance().addToken(this.token, position);
            GameBoard.getInstance().executeStrategyType(this, "tile");
        }
    }

    /**
     * Puts player in jail
     * @author walshj05
     */
    public void goToJail() {
        inJail = true;
        setPosition(10);
    }

    /** 
     * Checks to see if the player is in jail
     * @return boolean
     * @author walshj05
     */
    public boolean isInJail() {
        return inJail;
    }

    /** 
     * Releases player from jail
     * @author walshj05
     */
    public void releaseFromJail() {
        GameScene.sendAlert(name + " was released from jail!");
        inJail = false;
        jailTurns = 0;
    }

    /** 
     * Checks if the player has a monopoly
     *
     * @return boolean
     * @author walshj05
     */
    public boolean hasMonopoly(ColorGroup colorGroup) {
        for (Monopoly monopoly : monopolies) {
            if (monopoly.getColorGroup() == colorGroup) {
                return true;
            }
        }
        return false;
    }

    /** 
     * Adds a certain amount to the player's balance
     *
     * @param amount int amount
     * @author walshj05
     */
    public void addToBalance(int amount) {
        this.balance += amount;
    }

    /** 
     * Checks if the player has a certain property
     *
     * @param property String
     * @return boolean
     * @author walshj05
     */
    public boolean hasProperty(String property) {
        return propertiesOwned.contains(property);
    }

    /** 
     * Adds a card to the player's hand
     *
     * @param card String
     * @author walshj05
     */
    public void addCard(String card) {
        cards.add(card);
    }

    /** 
     * Removes a card from the player's hand
     *
     * @param card String
     * @author walshj05
     */
    public void removeCard(String card) {
        cards.remove(card);
    }

    /** 
     * Checks if the player has a certain community chest card
     *
     * @param card String
     * @return boolean
     * @author walshj05
     */
    public boolean hasCard(String card) {
        return cards.contains(card);
    }

    /** 
     * Checks if the player is bankrupt
     *
     * @return boolean
     * @author walshj05
     */
    public boolean isBankrupt() {
        return balance == 0;
    }

    /**
     * Returns a string representation of the player
     * @return String
     * @author walshj05
     */
    @Override
    public String toString() {
        return name + " (Token: " + token.getName() + ")";
    }

    /** 
     * Updates the monopolies of the player
     *
     * @author walshj05
     */
    public void updateMonopolies() {
        Banker banker = Banker.getInstance();
        banker.checkForMonopolies(propertiesOwned, monopolies, colorGroups);
        for (Monopoly monopoly : monopolies) {
            if (!colorGroups.contains(monopoly.getColorGroup())) {
                colorGroups.add(monopoly.getColorGroup());
            }
        }
    }
    /** 
     * Gives the number of hotels the player owns
     * @return numHotels
     * @author crevelings and walshj05
     */
    public int getNumHotels() {
        int numHotels = 0;
        for (Monopoly monopoly : monopolies) {
            int[] buildings = monopoly.getBuildings();
            for (int i = 0; i < buildings.length; i++) {
                if (buildings[i] == 5) {
                    numHotels++;
                }
            }
        }
        return numHotels;
    }

    /**
     * Gets the number of turns the player has been in jail
     *
     * @return int
     * @author walshj05
     */
    public int getJailTurns() {
        return jailTurns;
    }

    /** 
     * Gives the number of houses the player owns
     * @return numHouses
     * @author crevelings and walshj05
     */
    public int getNumHouses() {
        int numHouses = 0;
        for (Monopoly monopoly : monopolies) {
            int[] buildings = monopoly.getBuildings();
            for (int i = 0; i < buildings.length; i++) {
                if (buildings[i] > 0 && buildings[i] < 5) {
                    numHouses += buildings[i];
                }
            }
        }
        return numHouses;
    }

    public abstract void takeTurn(Dice dice );

    public abstract void purchaseProperty(String property, int price) throws InsufficientFundsException;

    public abstract void mortgageProperty(String property, int mortgageCost) throws NoSuchPropertyException;

    public abstract void sellProperty(String property, int propertyCost) throws NoSuchPropertyException;

    public abstract void subtractFromBalance(int amount);

    /**
     * Resets the number of turns the player has been in jail
     *
     * @author walshj05
     */
    public void resetJailTurns() {
        this.jailTurns = 0;
    }

    /**
     * Increments the number of turns the player has been in jail
     *
     * @author walshj05
     */
    public void incrementJailTurns() { //todo we can remove this once we make the jail process
        this.jailTurns++;
    }

    public ArrayList<ColorGroup> getColorGroups() {
        return colorGroups;
    }

    public abstract void buyHouse(String propertyName, ColorGroup colorGroup, int price);
    public abstract void sellHouse(String propertyName, ColorGroup colorGroup);
    public abstract void buyHotel(String propertyName, ColorGroup colorGroup, int price);
    public abstract void sellHotel(String propertyName, ColorGroup colorGroup);

    /**
     * Quits the game for the player.
     * @param player The player who is quitting the game.
     *
     * Developed by: shifmans
     */
    public void quitGame(Player player) {
        TurnManager.getInstance().removePlayer(player);
    }

    /**
     * Checks if the player has accepted a trade.
     *
     * Developed by: shifmans
     */
    public boolean hasAcceptedTrade(Player responder, Scanner keyboard) {
        System.out.println(responder.getName() + ", do you accept the trade (Y/N)? ");
        char answer = keyboard.next().charAt(0);

        while (answer != 'Y' && answer != 'y' && answer != 'N' && answer != 'n') {
            System.out.println("Invalid response, " + responder.getName() + " do you accept the trade (Y/N)? ");
            answer = keyboard.next().charAt(0);
        }

        return (answer == 'Y') || (answer == 'y');
    }
}