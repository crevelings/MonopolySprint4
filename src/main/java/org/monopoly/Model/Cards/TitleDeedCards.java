package org.monopoly.Model.Cards;

import org.monopoly.Model.GameBoard;
import org.monopoly.Model.GameTiles.*;
import java.util.*;

/**
 * TitleDeedCards class represents the title deed cards in the game of Monopoly.
 * It initializes the properties with their respective details such as name, price, rent prices, color group, etc.
 *
 * Developed by: shifmans
 */
public class TitleDeedCards {
    private final HashMap<String, GameTile> properties;
    private static TitleDeedCards instance;

    /**
     * Constructor for TitleDeedCards.
     * Initializes the properties with their respective details.
     *
     * Developed by: shifmans
     */
    public TitleDeedCards() {
        this.properties = new HashMap<>();
        initializeCards();
    }

    /**
     * Made into singleton
     * @return instance
     * @author crevelings (4/8/25)
     */
    public static TitleDeedCards getInstance() {
        if (instance == null) {
            instance = new TitleDeedCards();
        }
        return instance;
    }

    /**
     * Resets the instance of TitleDeedCards for testing purposes.
     *
     * Developed by: shifmans
     */
    public static void resetInstance() {
        instance = new TitleDeedCards();
    }

    /**
     * Initializes the properties with their respective details.
     *
     * Developed by: shifmans
     */
    private void initializeCards() {
        GameBoard board = GameBoard.getInstance();

        properties.put("Mediterranean Avenue", board.getTile(1));
        properties.put("Baltic Avenue", board.getTile(3));

        properties.put("Oriental Avenue", board.getTile(6));
        properties.put("Vermont Avenue", board.getTile(8));
        properties.put("Connecticut Avenue", board.getTile(9));

        properties.put("St. Charles Place", board.getTile(11));
        properties.put("States Avenue", board.getTile(13));
        properties.put("Virginia Avenue", board.getTile(14));

        properties.put("St. James Place", board.getTile(16));
        properties.put("Tennessee Avenue", board.getTile(18));
        properties.put("New York Avenue", board.getTile(19));

        properties.put("Kentucky Avenue", board.getTile(21));
        properties.put("Indiana Avenue", board.getTile(23));
        properties.put("Illinois Avenue", board.getTile(24));

        properties.put("Atlantic Avenue", board.getTile(26));
        properties.put("Ventnor Avenue", board.getTile(27));
        properties.put("Marvin Gardens", board.getTile(29));

        properties.put("Pacific Avenue", board.getTile(31));
        properties.put("North Carolina Avenue", board.getTile(32));
        properties.put("Pennsylvania Avenue", board.getTile(34));

        properties.put("Park Place", board.getTile(37));
        properties.put("Boardwalk", board.getTile(39));

        // Railroads
        properties.put("Reading Railroad", board.getTile(5));
        properties.put("Pennsylvania Railroad", board.getTile(15));
        properties.put("B&O Railroad", board.getTile(25));
        properties.put("Short Line Railroad", board.getTile(35));

        // Utilities
        properties.put("Electric Company", board.getTile(12));
        properties.put("Water Works", board.getTile(28));
    }

    /**
     * Returns the properties of the TitleDeedCards.
     * @return HashMap of properties.
     *
     * Developed by: shifmans
     */
    public HashMap<String, GameTile> getProperties() {
        return this.properties;
    }

    /**
     * Returns a specific property based on its name.
     * @param propertyName Name of the property.
     * @return GameTile object representing the property.
     *
     * Developed by: shifmans
     */
    public GameTile getProperty(String propertyName) {
        return this.properties.get(propertyName);
    }
}
