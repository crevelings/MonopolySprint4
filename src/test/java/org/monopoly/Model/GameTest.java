package org.monopoly.Model;

import org.junit.jupiter.api.Test;
import org.monopoly.Model.Players.HumanPlayer;
import org.monopoly.Model.Players.Player;
import org.monopoly.Model.Players.Token;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}