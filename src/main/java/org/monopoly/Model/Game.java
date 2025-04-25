package org.monopoly.Model;

import org.monopoly.Model.Players.Player;
import java.util.ArrayList;

/**
 * Represents the game logic and state for a game of Monopoly
 * @author walshj05
 * Modified by: crevelings (4/1/25)
 */
public class Game {
    private TurnManager turnManager;

    /**
     * Constructor for the Game class
     * @author walshj05
     */
    public Game(ArrayList<Player> humanPlayers, ArrayList<Player> computerPlayers) {
        ArrayList<Player> players = new ArrayList<>();
        players.addAll(humanPlayers);
        players.addAll(computerPlayers);
        this.turnManager = new TurnManager(players.size(), players); // todo implement players rolling for order
    }
    public void start() {
        turnManager.playerTakeTurn();
    }
}
