package org.monopoly.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.monopoly.Model.Players.ComputerPlayer;
import org.monopoly.Model.Players.Player;
import org.monopoly.View.GameScene.GameScene;

public class ComputerPlayerController {
    @FXML
    private AnchorPane playerPane;
    @FXML
    private Label name;
    @FXML
    private ImageView token;
    @FXML
    private Label balance;
    @FXML
    private VBox properties;
    private Player player;

    @FXML
    private void initialize() {
        // Initialize the player interface if needed
        // For example, set a default image or style
        playerPane.setVisible(true);
    }


    /**
     * Initializes the player interface with the given player.
     * @param player The player to initialize the interface for.
     * @author walshj05
     */
    public void setPlayer(ComputerPlayer player) {
        this.player = player;
        this.token.setImage(new Image(GameScene.addFilePath(player.getToken().getIcon())));
        name.setText(player.getName());
        balance.setText("Balance: $" + player.getBalance());
        player.setController(this);
        // Update properties list if needed
    }

    /**
     * Updates the balance on the player interface.
     * @author walshj05
     */
    public void updateBalance() {
        balance.setText("Balance: $" + player.getBalance());
    }

    public void updateProperties() {
        properties.getChildren().clear();
        for (String property : player.getPropertiesOwned()) {
            Label propertyLabel = new Label(property);
            properties.getChildren().add(propertyLabel);
        }
    }
}
