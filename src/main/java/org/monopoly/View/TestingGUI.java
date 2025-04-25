package org.monopoly.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import org.monopoly.View.GameScene.GameScene;

import java.io.IOException;
import java.util.Objects;

/**
 * A class that we can use to test features of JavaFX in a isolated environment.
 * This can be removed once we have our final product.
 * @author walshj05
 */
public class TestingGUI extends Application {

    /**
     * Starts the JavaFX application.
     * @param stage the primary stage for this application
     * @author walshj05
     */
    @Override
    public void start(Stage stage) throws IOException {
        //Bank Info
        //String fxmlPath = "/org/monopoly/View/GameScene/Bank/BankInfo.fxml";
        //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
        //stage.setTitle("Bank Info");
        //stage.setScene(new Scene(root, 740, 740));
        //stage.show();

        //Auction
        //String fxmlPath2 = "/org/monopoly/View/GameScene/Auction/Auction.fxml";
        //Parent root2 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath2)));
        //stage.setTitle("Auction");
        //stage.setScene(new Scene(root2, 740, 400));
        //stage.show();

        //Trade
        String fxmlPath3 = "/org/monopoly/View/GameScene/Trade/Trade.fxml";
        Parent root3 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath3)));
        stage.setTitle("Trade");
        stage.setScene(new Scene(root3, 370, 400));
        stage.show();
    }

    /**
     * Main method to launch the application.
     * @param args command line arguments
     * @author walshj05
     */
    public static void main(String[] args) {
        launch();
    }
}
