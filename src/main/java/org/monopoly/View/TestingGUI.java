package org.monopoly.View;
import javafx.application.Application;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import org.monopoly.View.GameScene.GameScene;

public class TestingGUI extends Application {
    @Override
    public void start(Stage stage) {
        AudioClip plonkSound = new AudioClip(GameScene.addFilePath("RollSound.wav"));
        plonkSound.play();
    }

    public static void main(String[] args) {
        launch();
    }
}
