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
import java.net.URL;

public class BottomPanelController {
    @FXML
    private Button bankerInfo;
    @FXML
    private Button quitGame;
    @FXML
    private Button settings;

    @FXML
    public void onBankerInfoClick() throws IOException {

    }

    @FXML
    public void onQuitGameClick() {
        System.out.println("Quit Game Clicked");
    }

    @FXML
    public void onSettingsClick() {
        System.out.println("Settings Clicked");
    }
}
