package org.monopoly.Model;

import org.junit.jupiter.api.Test;
import org.monopoly.Model.Players.HumanPlayer;
import org.monopoly.Model.Players.Player;
import org.monopoly.Model.Players.Token;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the Game class.
 * @author walshj05
 * tests added by shifmans
 */
class GameTest {
    @Test
    void testGameStartingBeginsCurrentPlayersTurn() {
        ArrayList<Player> humanPlayers = new ArrayList<>();
        humanPlayers.add(new HumanPlayer("p1", new Token("Dog", "ScottieDog.png")));
        humanPlayers.add(new HumanPlayer("p2", new Token("Battleship", "BattleShip.png")));

        Game game = new Game(humanPlayers, new ArrayList<Player>());
        game.start();
        assertEquals("p1", TurnManager.getInstance().getCurrentPlayer().getName());
    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testNextPlayer() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new HumanPlayer("Player1", new Token("Dog", "ScottieDog.png")));
        players.add(new HumanPlayer("Player2", new Token("Car", "Car.png")));
        players.add(new HumanPlayer("Player3", new Token("Hat", "TopHat.png")));
        TurnManager turnManager = new TurnManager(players.size(), players);

        assertEquals("Player1", turnManager.getCurrentPlayer().getName());

        turnManager.nextPlayer();
        assertEquals("Player2", turnManager.getCurrentPlayer().getName());

        turnManager.nextPlayer();
        assertEquals("Player3", turnManager.getCurrentPlayer().getName());

        turnManager.nextPlayer();
        assertEquals("Player1", turnManager.getCurrentPlayer().getName());
    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testNextPlayerTakeTurn() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new HumanPlayer("Player1", new Token("Dog", "ScottieDog.png")));
        players.add(new HumanPlayer("Player2", new Token("Car", "Car.png")));
        players.add(new HumanPlayer("Player3", new Token("Hat", "TopHat.png")));
        TurnManager turnManager = new TurnManager(players.size(), players);

        assertEquals("Player1", turnManager.getCurrentPlayer().getName());

        turnManager.nextPlayersTurn();
        assertTrue(turnManager.getCurrentPlayer().getPosition() != 50);
        assertEquals("Player2", turnManager.getCurrentPlayer().getName());

        turnManager.nextPlayersTurn();
        assertEquals("Player3", turnManager.getCurrentPlayer().getName());
        assertTrue(turnManager.getCurrentPlayer().getPosition() != -50);
    }

    /**
     * Developed by: shifmans
     */
    @Test
    public void testGetInstance() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new HumanPlayer("Player1", new Token("Dog", "ScottieDog.png")));
        players.add(new HumanPlayer("Player2", new Token("Car", "Car.png")));
        players.add(new HumanPlayer("Player3", new Token("Hat", "TopHat.png")));
        TurnManager turnManager = new TurnManager(players.size(), players);

        assertEquals(turnManager.getTurnManager(), TurnManager.getInstance());
    }
}