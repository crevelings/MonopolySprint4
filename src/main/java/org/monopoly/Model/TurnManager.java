package org.monopoly.Model;

import org.monopoly.Model.Players.Player;

import java.util.ArrayList;

/**
 * Manages the turns of the players in the game.
 * Keeps track of the current player and the order of the players.
 * @author walshj05
 */
public class TurnManager {
    private int currentPlayerIndex;
    private final ArrayList<Player> players;
    private int numPlayers;
    private static TurnManager instance;

    public static TurnManager getInstance() {
        if (instance == null) {
            return new TurnManager(0, new ArrayList<>());
        }
        return instance;
    }
    /**
     * Constructor for the TurnManager class.
     * @param numPlayers the number of players in the game
     * @param players the list of players in the game
     * @author walshj05
     */
    public TurnManager(int numPlayers, ArrayList<Player> players) {
        this.numPlayers = numPlayers;
        this.currentPlayerIndex = 0;
        this.players = players;
        instance = this;
    }

    /**
     * Continues to the next player in the turn order.
     * @author walshj05
     */
    public void nextPlayer() {
        if (numPlayers == 0) { // on get instance where game never initializes
            return;
        }
        currentPlayerIndex = (currentPlayerIndex + 1) % numPlayers;
    }

    /**
     * Gets the current player
     * @return the current player
     * @author walshj05
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }


    /**
     * Allows the current player to take their turn
     * @author walshj05
     * Modified by: crevelings (4/1/25), (4/9/25)
     * 4/9/25: Moved from game to TurnManager
     */
    public void playerTakeTurn(){
        players.get(currentPlayerIndex).takeTurn(Dice.getInstance());
    }

    /**
     * Continues to the next players turn (ends the current players turn)
     * @author walshj05
     * Modified by: crevelings (4/1/25), (4/9/25)
     * 4/9/25: Moved from game to TurnManager
     */
    public void nextPlayersTurn(){
        nextPlayer();
    }

    /**
     * Removes a player from the game.
     * @param player the player to be removed from the game
     * @author walshj05
     */
    public void removePlayer(Player player) {
        players.remove(player);
        numPlayers--;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Gets the TurnManager object for testing purposes
     * @return TurnManager object
     * @author walshj05
     */
    public TurnManager getTurnManager() {
        return instance;
    }
}
