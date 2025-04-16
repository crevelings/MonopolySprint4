package org.monopoly.View.GameScene.Board;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.monopoly.Model.Players.Token;
import org.monopoly.View.GameScene.GameScene;

import java.util.ArrayList;

/**
 * Controller for the property tile.
 * This class handles the display of tokens on the property tile.
 * @author walshj05
 */
public class PropertyTileController implements TileController {
    @FXML
    private ImageView buildings;
    @FXML
    private AnchorPane tile;
    @FXML
    private ImageView token1;
    @FXML
    private ImageView token2;
    @FXML
    private ImageView token3;
    @FXML
    private ImageView token4;


    /**
     * Initializes the tile with default settings.
     *
     * @author walshj05
     */
    public void initialize() {
        // Initialize the tile if needed
        // For example, set a default image or style
        tile.setVisible(true);
    }

    /**
     * Rotates the tile by the specified degrees.
     *
     * @param degrees The degrees to rotate the tile.
     * @author walshj05
     */
    public void rotatePane(int degrees) {
        tile.setRotate(tile.getRotate() + degrees);
        if (degrees == 270 || degrees == 90) {
            tile.translateXProperty().set(20);
        }
        RegularGameTileController.atTopOfBoard(degrees, token1, token2, token3, token4);
    }

    /**
     * Updates the tokens to display on the tile
     *
     * @param tokens The tokens to display on the tile.
     * @author walshj05
     */
    @Override
    public void updateTokens(ArrayList<Token> tokens) {
        cancelAllTokens();
        if (tokens.size() > 4 || tokens.isEmpty()) { // assuming max 4 tokens on a tile
            return;
        }
        token1.setImage(new Image(GameScene.addFilePath(tokens.getFirst().getIcon())));
        if (tokens.size() > 1) {
            token2.setImage(new Image(GameScene.addFilePath(tokens.get(1).getIcon())));
        }
        if (tokens.size() > 2) {
            token3.setImage(new Image(GameScene.addFilePath(tokens.get(2).getIcon())));
        }
        if (tokens.size() > 3) {
            token4.setImage(new Image(GameScene.addFilePath(tokens.get(3).getIcon())));
        }
    }

    /**
     * Cancels all tokens on the tile.
     *
     * @author walshj05
     */
    private void cancelAllTokens() {
        token1.setImage(null);
        token2.setImage(null);
        token3.setImage(null);
        token4.setImage(null);
    }

    /**
     * Updates the buildings on the tile.
     *
     * @param numBuildings The number of buildings to display.
     * @author shifmans
     */
    @Override
    public void updateBuildings(int numBuildings) {
        buildings.setLayoutX(0);
        if (numBuildings == 1) {
            buildings.setImage(new Image(GameScene.addFilePath("oneHouse.png")));
        } else if (numBuildings == 2) {
            buildings.setImage(new Image(GameScene.addFilePath("twoHouse.png")));
        } else if (numBuildings == 3) {
            buildings.setImage(new Image(GameScene.addFilePath("threeHouse.png")));
        } else if (numBuildings == 4) {
            buildings.setImage(new Image(GameScene.addFilePath("fourHouse.png")));
        } else if (numBuildings == 5) {
            buildings.setImage(new Image(GameScene.addFilePath("Hotel.png")));
            buildings.setLayoutX(20);
        }
    }
}
