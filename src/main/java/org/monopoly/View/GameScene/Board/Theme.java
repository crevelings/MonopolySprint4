package org.monopoly.View.GameScene.Board;

public enum Theme {
    CLASSIC("Classic", ""),
    DARK("Dark Mode", "dark-theme.css"),
    MODERN("Modern", "modern-theme.css"),
    RETRO("Retro", "retro-theme.css");

    private final String displayName;
    private final String stylesheetPath;

    Theme(String displayName, String stylesheetPath) {
        this.displayName = displayName;
        this.stylesheetPath = stylesheetPath;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getStylesheetPath() {
        return "/styles/" + stylesheetPath;  // Assuming CSS files are in resources/styles/
    }

    @Override
    public String toString() {
        return displayName;
    }
}