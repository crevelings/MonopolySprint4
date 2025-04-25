package org.monopoly.View.GameScene.Board;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the bottom panel of the game.
 */
public class BottomPanelController implements Initializable {
    @FXML
    private ComboBox<Theme> themeComboBox;
    @FXML
    private Button bankerInfo;
    @FXML
    private Button quitGame;
    @FXML
    private Button settings;

    private final ThemeManager themeManager = ThemeManager.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (themeComboBox != null) {
            themeComboBox.getItems().addAll(Theme.values());
            themeComboBox.setValue(Theme.CLASSIC);

            // Apply classic theme (will use default JavaFX styling)
            themeManager.setTheme(Theme.CLASSIC, themeComboBox.getScene());
            applyThemeToAllWindows(Theme.CLASSIC);

            themeComboBox.setOnAction(event -> {
                Theme selectedTheme = themeComboBox.getValue();
                themeManager.setTheme(selectedTheme, themeComboBox.getScene());
                applyThemeToAllWindows(selectedTheme);
            });
        }
    }

    private void applyThemeToAllWindows(Theme theme) {
        for (Window window : Stage.getWindows()) {
            if (window instanceof Stage) {
                Scene scene = ((Stage) window).getScene();
                if (scene != null) {
                    scene.getStylesheets().clear();
                    scene.getStylesheets().add(
                            getClass().getResource(theme.getStylesheetPath()).toExternalForm()
                    );
                }
            }
        }
    }

    private void applyTheme(Theme theme) {
        Scene scene = themeComboBox.getScene();
        if (scene != null) {
            // Clear existing theme stylesheets
            scene.getStylesheets().clear();
            // Add new theme stylesheet
            scene.getStylesheets().add(
                    getClass().getResource(theme.getStylesheetPath()).toExternalForm()
            );
        }
    }

    @FXML
    public void handleClose(javafx.event.ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Configures and has the pop-up for the Banker Info screen to show the houses and hotels owned.
     * Along with the exit button to close the pop-up.
     */
    @FXML
    public void onBankerInfoClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/monopoly/View/GameScene/Bank/BankInfo.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("ABC");
        stage.setScene(new Scene(root1));
        stage.show();

        Button exitButton = (Button) root1.lookup("#bankInfoExitButton");
        if (exitButton != null) {
            exitButton.setOnAction(event -> stage.close());
        }
    }

    @FXML
    public void onQuitGameClick() {
        System.out.println("Quit Game Clicked");
    }

    @FXML
    public void onSettingsClick() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/monopoly/View/GameScene/Settings/Settings.fxml"));
        try {
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Settings");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
