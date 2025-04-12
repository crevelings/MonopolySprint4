package org.monopoly.View;


import javafx.application.Application;
import javafx.stage.Stage;
import org.monopoly.Model.Players.Token;
import org.monopoly.View.GameScene.GameScene;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Main class to run the Monopoly game
 * @author walshj05
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GUI monopolyGUI = new GUI(stage);
        ArrayList<Token> tokens = new ArrayList<>();
        ArrayList<Token> empty = new ArrayList<>();
        String[] images = {"RaceCar", "ScottieDog", "TopHat", "Battleship"};
        for (int i = 0; i < 4; i++) {
            tokens.add(new Token(images[i], images[i] + ".png"));
        }
        GameScene.getInstance().updateTokens(tokens, 0);
        for (int i = 0; i < 39; i++) {
            GameScene.getInstance().updateTokens(tokens, i + 1);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
