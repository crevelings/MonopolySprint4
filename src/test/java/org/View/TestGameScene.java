package org.View;

import org.junit.jupiter.api.Test;
import org.monopoly.Model.Players.Token;
import org.monopoly.View.GameScene.GameScene;

import static org.junit.jupiter.api.Assertions.*;

public class TestGameScene {
    @Test
    void testAddFilePathReturnsFilePathOfToken() {
        String tokenName = "BattleShip";
        assertEquals("file:src/main/resources/org/monopoly/View/GameScene/TokenPNGs/BattleShip.png",
                GameScene.addFilePath(tokenName + ".png"));
    }

    @Test
    void retrievingTokenNotInResourcesReturnsErrorPNG(){
        String tokenName = "NotAToken";
        assertEquals("file:src/main/resources/org/monopoly/View/GameScene/TokenPNGs/error.png",
                GameScene.addFilePath(tokenName + ".png"));
    }

    @Test
    void testThisSameErrorFileAggregatesFromTokenObject(){
        Token token = new Token("WarShip", "WarShip.png");
        assertEquals("file:src/main/resources/org/monopoly/View/GameScene/TokenPNGs/error.png",
                GameScene.addFilePath(token.getIcon()));
    }
}
