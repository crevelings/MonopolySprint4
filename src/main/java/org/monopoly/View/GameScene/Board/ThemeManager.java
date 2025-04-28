package org.monopoly.View.GameScene.Board;

import javafx.scene.Scene;

/**
 * Class for managing themes
 * @author crevelings
 */
public class ThemeManager {
    private static ThemeManager instance;

    /**
     * Singleton pattern setup
     */
    public static ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }

    /**
     * Sets the theme for a given scene
     * @author crevelings
     */
    public void setTheme(Theme theme, Scene scene) {
        if (scene != null) {
            // Clear existing theme stylesheets
            scene.getStylesheets().clear();
            // Add new theme stylesheet
            scene.getStylesheets().add(
                    getClass().getResource(theme.getStylesheetPath()).toExternalForm()
            );
            String stylesheetPath = theme.getStylesheetPath();
            if (!stylesheetPath.isEmpty()) {
                scene.getStylesheets().add(
                        getClass().getResource(stylesheetPath).toExternalForm()
                );
            }
        }
    }
}
