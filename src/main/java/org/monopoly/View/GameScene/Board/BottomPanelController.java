package org.monopoly.View.GameScene.Board;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.media.MediaPlayer;
import javafx.stage.*;
import org.monopoly.Model.Banker;
import org.monopoly.Model.Players.HumanPlayer;
import org.monopoly.Model.TurnManager;

import java.io.ByteArrayInputStream;
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

    @FXML
    private CheckBox animationsCheckBox;
    @FXML
    private CheckBox soundEffectsCheckBox;

    private boolean animationsEnabled = true;
    private boolean soundEffectsEnabled = true;

    private MediaPlayer mediaPlayer;
    private boolean isMusicPlaying = false;

    // Add these methods:
    public boolean areAnimationsEnabled() {
        return animationsEnabled;
    }

    public boolean areSoundEffectsEnabled() {
        return soundEffectsEnabled;
    }

    @FXML
    private void handleAnimationsToggle() {
        animationsEnabled = animationsCheckBox.isSelected();
        // You can add animation-specific logic here
    }

    @FXML
    private void handleSoundEffectsToggle() {
        soundEffectsEnabled = soundEffectsCheckBox.isSelected();
        // You can add sound-specific logic here
    }

    /**
     * Starts up the theme manager and applies theme to all windows
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (themeComboBox != null) {
            themeComboBox.getItems().addAll(Theme.values());
            themeComboBox.setValue(Theme.NORMAL);

            themeManager.setTheme(Theme.NORMAL, themeComboBox.getScene());
            applyThemeToAllWindows(Theme.NORMAL);

            themeComboBox.setOnAction(_ -> {
                Theme selectedTheme = themeComboBox.getValue();
                themeManager.setTheme(selectedTheme, themeComboBox.getScene());
                applyThemeToAllWindows(selectedTheme);
            });
        }
    }

    /**
     * Configures and has the pop-up for the Banker Info screen to show the houses and hotels owned.
     * Along with the exit button to close the pop-up.
     */
    @FXML
    public void onBankerInfoClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/monopoly/View/GameScene/Bank/BankInfo.fxml"));
        Parent root1 = fxmlLoader.load();

        BankInfoController controller = fxmlLoader.getController();
        Banker banker = Banker.getInstance();
        controller.updateCounts(
                banker.getHouses(),
                banker.getHotels(),
                banker.getBankNumProperties()
        );

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("ABC");
        stage.setScene(new Scene(root1));
        stage.show();

        Button exitButton = (Button) root1.lookup("#bankInfoExitButton");
        if (exitButton != null) {
            exitButton.setOnAction(_ -> stage.close());
        }
    }

    /**
     * Handles the end game button click event.
     *
     * Developed by: shifmans
     */
    @FXML
    public void onEndGameClick() {
        TurnManager turnManager = TurnManager.getInstance();
        long humanPlayerCount = turnManager.getPlayers().stream()
                .filter(player -> player instanceof HumanPlayer)
                .count();

        if (humanPlayerCount == 1) {
            HumanPlayer player = (HumanPlayer) turnManager.getPlayers().get(0);
            String simulatedInput = "Y\n";
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
            player.endGame();
            System.exit(0);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("End Game Confirmation");
            alert.setContentText("Do all players want to end the game?");

            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            alert.getButtonTypes().setAll(yesButton, noButton);

            alert.showAndWait().ifPresent(response -> {
                if (response == yesButton) {
                    String simulatedInput = "Y\n";
                    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
                    ((HumanPlayer) turnManager.getCurrentPlayer()).endGame();
                    System.exit(0);
                }
                else if (response == noButton) {
                    Alert declineAlert = new Alert(Alert.AlertType.INFORMATION);
                    declineAlert.setTitle("End Game Declined");
                    declineAlert.setHeaderText(null);
                    declineAlert.setContentText("Not all of the players agreed to end the game.");
                    declineAlert.showAndWait();
                }
            });
        }
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

    /**
     * Applies the given theme to all open windows.
     *
     * @param theme The theme to apply.
     */
    private void applyThemeToAllWindows(Theme theme) {
        for (Window window : Stage.getWindows()) {
            if (window instanceof Stage) {
                Scene scene = window.getScene();
                if (scene != null) {
                    scene.getStylesheets().clear();
                    scene.getStylesheets().add(
                            getClass().getResource(theme.getStylesheetPath()).toExternalForm()
                    );
                }
            }
        }
    }

    /**
     * Closes settings window
     * @param event
     */
    @FXML
    public void handleClose(javafx.event.ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void toggleMusic() {
        if (mediaPlayer != null) {
            if (isMusicPlaying) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.play();
            }
            isMusicPlaying = !isMusicPlaying;
        }
    }

    @FXML
    private void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }
}
