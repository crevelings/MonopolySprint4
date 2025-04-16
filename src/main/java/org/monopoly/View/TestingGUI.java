package org.monopoly.View;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import org.monopoly.View.GameScene.GameScene;

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
    public void start(Stage stage) {

        Scene scene = new Scene(new Pane());
        stage.setTitle("Monopoly");
        ((Pane) scene.getRoot()).setPrefSize(800,600);
        stage.setScene(scene);
        stage.show();

        GameScene.sendAlert("This is a test alert");
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
