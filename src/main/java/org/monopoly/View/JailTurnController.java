package org.monopoly.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.monopoly.Model.Dice;
import org.monopoly.Model.GameBoard;
import org.monopoly.Model.Players.HumanPlayer;
import org.monopoly.View.GameScene.GameScene;

/**
 * Controller for the JailTurn view.
 * @author walshj05
 */
public class JailTurnController {
    @FXML
    private Button payButton;
    @FXML
    private ImageView tokenView;
    @FXML
    private Button getOutOfJailCard;
    @FXML
    private Button rollButton;
    private HumanPlayer player;

    /**
     * Sets the player for the JailTurnController
     * @author walshj05
     */
    public void setPlayer(HumanPlayer player) {
        this.player = player;
        String tokenIcon = player.getToken().getIcon();
        tokenView.setImage(new Image(GameScene.addFilePath(tokenIcon)));
        Dice.getInstance().resetNumDoubles();
        if (player.hasCard("community:Get Out of Jail Free") ||player.hasCard("chance:Get Out of Jail Free.")){
            getOutOfJailCard.setDisable(false);
        } else {
            getOutOfJailCard.setDisable(true);
        }
    }

    /**
     * Handles the action when the player clicks the "Pay" button.
     * @author walshj05
     */
    public void onPay(ActionEvent actionEvent) {
        if (player.getBalance() >= 50) {
            player.subtractFromBalance(50);
            player.releaseFromJail();
            player.takeTurn(Dice.getInstance());
        } else {
            GameScene.sendAlert("You do not have a Get Out of Jail Free card.");
        }
        disableTurnButtons();
    }

    public void onRoll(ActionEvent actionEvent) {
        Dice.getInstance().roll();
        if (Dice.getInstance().isDouble()){
            Dice.getInstance().resetNumDoubles();
            GameScene.sendAlert("You rolled a double! You are free to go.");
            player.releaseFromJail();
            player.takeTurn(Dice.getInstance());
        } else if (player.getJailTurns() == 3) {
            GameScene.sendAlert("You have been in jail for three turns, you're free to go.");
            player.releaseFromJail();
            player.takeTurn(Dice.getInstance());
        } else {
            GameScene.sendAlert("You did not roll a double! You have " + (3 - player.getJailTurns()) + " turns left in jail.\n" +
                    "Manage your assets or end your turn.");
        }
        disableTurnButtons();
    }

    public void onUseCard(ActionEvent actionEvent) {
        if (player.hasCard("community:Get Out of Jail Free")){
            player.removeCard("community:Get Out of Jail Free");
            GameBoard.getInstance().executeStrategyType(player, "community:Get Out of Jail Free");
            player.takeTurn(Dice.getInstance());
        }else if (player.hasCard("chance:Get Out of Jail Free.")) {
            player.removeCard("chance:Get Out of Jail Free.");
            GameBoard.getInstance().executeStrategyType(player, "chance:Get Out of Jail Free.");
            player.takeTurn(Dice.getInstance());
        } else {
            GameScene.sendAlert("You do not have a Get Out of Jail Free card.");
        }
        disableTurnButtons();
    }

    private void disableTurnButtons() {
        payButton.setDisable(true);
        getOutOfJailCard.setDisable(true);
        rollButton.setDisable(true);
    }
}
