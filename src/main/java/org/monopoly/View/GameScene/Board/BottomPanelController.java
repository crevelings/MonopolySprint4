package org.monopoly.View.GameScene.Board;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Controller class for the bottom panel of the game.
 */
public class BottomPanelController {
    @FXML
    private Button bankerInfo;
    @FXML
    private Button quitGame;
    @FXML
    private Button settings;

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

    }

    @FXML
    public void onSettingsClick() {
        System.out.println("Settings Clicked");
    }
}
