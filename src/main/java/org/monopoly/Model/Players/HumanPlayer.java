package org.monopoly.Model.Players;

import org.monopoly.Model.*;

import org.monopoly.Model.Cards.ColorGroup;
import org.monopoly.Model.Cards.TitleDeedCards;
import org.monopoly.Model.GameTiles.PropertySpace;
import org.monopoly.View.GUI;
import org.monopoly.View.GameScene.GameScene;
import org.monopoly.View.HumanPlayerController;


import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class representing a player in the Monopoly game.
 * More functionality will be added
 * @author walshj05, crevelings
 */
public class HumanPlayer extends Player {
    private HumanPlayerController controller;

    /**
     * Constructor for a HumanPlayer
     * @param name String
     * @param token Token
     * @author walshj05
     */
    public HumanPlayer(String name, Token token) {
        super(name, token);
    }

    public void setController(HumanPlayerController controller) {
        this.controller = controller;
    }

    /**
     * A method for a player to take a turn in the game
     * @param dice Dice object
     * @author walshj05
     */
    public void takeTurn (Dice dice) {
        if (GUI.getInstance() != null) {
            controller.startTurn();
        }
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

    @Override
    public void addToBalance(int amount) {
        super.addToBalance(amount);
        updateObservers();
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
            updateObservers();
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
            updateObservers();
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
            updateObservers();
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
            updateObservers();
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

    /**
     * Ends the Monopoly game.
     *
     * Developed by: shifmans
     */
    public void endGame() {
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Do all players want to end the game (Y/N)? ");
        char answer = keyboard.next().charAt(0);

        while (answer != 'Y' && answer != 'y' && answer != 'N' && answer != 'n') {
            System.out.println("Invalid response, do all players want to end the game (Y/N)? ");
            answer = keyboard.next().charAt(0);
        }

        if (answer == 'Y' || answer == 'y') {
            System.out.println("Game Over! The winner is " + getWealthiestPlayer().getName() + " with $" + getWealthiestPlayer().getBalance() + "!");
            removePlayersFromGame();
        }
        else
        {
            System.out.println("Not all players voted to end the game. The game will continue.");
        }
    }

    /**
     * Gets the wealthiest player in the game.
     * @return The wealthiest player.'
     *
     * Developed by: shifmans
     */
    private Player getWealthiestPlayer() {
        TurnManager tm = TurnManager.getInstance();
        ArrayList<Player> players = tm.getPlayers();
        Player wealthiestPlayer = players.getFirst();

        for (Player player : players) {
            if (wealthiestPlayer.getBalance() < player.getBalance()) {
                wealthiestPlayer = player;
            }
        }

        return wealthiestPlayer;
    }

    /**
     * Quits the game for all players when game is ended.
     *
     * Developed by: shifmans
     */
    private void removePlayersFromGame() {
        TurnManager tm = TurnManager.getInstance();

        for (Player player : new ArrayList<>(tm.getPlayers())) {
                quitGame(player);
        }
    }

    private void updateObservers() {
        if (controller != null) {
            controller.updatePlayerInfo();
        }
    }

    /**
     * Initiates a trade between two players.
     * @param trader The player who is initiating the trade.
     *
     * Developed by: shifmans
     */
    public void initiateTrade(Player trader) {
        Scanner keyboard = new Scanner(System.in);

        System.out.println(trader.getName() + ", who do you want to trade with? ");
        String otherPlayer = keyboard.nextLine();
        Player responder = getPlayerByName(otherPlayer);

        while (responder == null) {
            System.out.println("No player found! " + trader.getName() + ", who do you want to trade with? ");
            otherPlayer = keyboard.nextLine();
            responder = getPlayerByName(otherPlayer);
        }

        System.out.println("What do you want to trade? ");
        String item = keyboard.nextLine();

        if (item.equalsIgnoreCase("property")) {
            System.out.println("What property do you want to trade? ");
            item = keyboard.nextLine();

            while (!hasItem(trader, item)) {
                System.out.println("Invalid property! What property do you want to trade? ");
                item = keyboard.nextLine();
            }
        }

        else {
            item = "Get Out of Jail Free Card";

            if (!hasItem(trader, item)) {
                System.out.println("You do not own this item. What do you want to trade? ");
                item = keyboard.nextLine();
                hasItem(trader, item);
            }
        }

        System.out.println("What price do you want to trade it for? ");
        int price = keyboard.nextInt();

        System.out.println(trader.getName() + " wants to trade " + item + " for $" + price + " with " + responder.getName() + ".");

        if (hasAcceptedTrade(responder, keyboard)) {
            performTrade(trader, responder, item, price);
            System.out.println("Trade accepted!");

        } else {
            System.out.println("Trade rejected.");
        }
    }

    /**
     * Gets the player by name.
     * @param responder The name of the player to find.
     * @return The player object if found, null otherwise.
     *
     * Developed by: shifmans
     */
    private Player getPlayerByName(String responder) {
        for (Player player : TurnManager.getInstance().getPlayers()) {
            if (player.getName().equalsIgnoreCase(responder)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Checks if the player has the item they want to trade.
     * @param trader The player who is trading.
     * @param item The item being traded.
     * @return Whether trader has the item or not.
     *
     * Developed by: shifmans
     */
    private boolean hasItem(Player trader, String item) {
        if (item.equalsIgnoreCase("Get Out of Jail Free Card")) {
            return trader.hasCard("Get Out of Jail Free.");
        }
        else {
            return trader.getPropertiesOwned().contains(item) || trader.getPropertiesMortgaged().contains(item);
        }
    }

    /**
     * Performs the trade between two players.
     * @param trader The player who is trading.
     * @param responder The player who is responding to trade offer.
     * @param item The item being traded.
     * @param price The price being offered for the item.
     *
     * Developed by: shifmans
     */
    private void performTrade(Player trader, Player responder, String item, int price) {
        if (item.equalsIgnoreCase("Get Out of Jail Free Card")) {
            trader.removeCard("Get Out of Jail Free.");
            responder.addCard("Get Out of Jail Free.");
            responder.subtractFromBalance(price);
        }
        else {
            TitleDeedCards.getInstance().getProperty(item).setOwner(responder.getName());
            trader.getPropertiesOwned().remove(item);
            responder.getPropertiesOwned().add(item);
            responder.subtractFromBalance(price);
        }
    }
}
