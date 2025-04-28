package org.monopoly.View.GameScene.Board;

/**
 *  Enumeration of available themes.
 * @author crevelings
 */
public enum Theme {
    NORMAL("Normal", ""),
    CLASSIC("Classic", "classic-theme.css"),
    DARK("Dark Mode", "dark-theme.css"),
    MODERN("Modern", "modern-theme.css"),
    RETRO("Retro", "retro-theme.css");

    private final String displayName;
    private final String stylesheetPath;

    /**
     * Constructor
     * @author crevelings
     */
    Theme(String displayName, String stylesheetPath) {
        this.displayName = displayName;
        this.stylesheetPath = stylesheetPath;
    }

    /**
     * Returns the path to the CSS file for the theme
     * @author crevelings
     */
    public String getStylesheetPath() {
        return "/styles/" + stylesheetPath;  // Assuming CSS files are in resources/styles/
    }

    /**
     * Returns the display name of the theme
     * @author crevelings
     */
    @Override
    public String toString() {
        return displayName;
    }
}