package org.monopoly.View.GameScene.Board;

import javafx.scene.Scene;

public class ThemeManager {
    private static ThemeManager instance;
    private Theme currentTheme = Theme.NORMAL;


    public static ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }

    public void setTheme(Theme theme, Scene scene) {
        if (scene != null) {
            currentTheme = theme;
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

    public Theme getCurrentTheme() {
        return currentTheme;
    }

}
