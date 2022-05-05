package org.wildhamsters.battleships;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.wildhamsters.battleships.board.Board;
import org.wildhamsters.battleships.board.DefaultBoard;
import org.wildhamsters.battleships.board.FieldState;
import org.wildhamsters.battleships.configuration.GameSettings;
import org.wildhamsters.battleships.fleet.*;
import org.wildhamsters.battleships.play.GameRoom;

import java.util.ArrayList;
import java.util.List;

public class BattleshipsIT {

    @Test(dataProvider = "fieldList-provider", dataProviderClass = FleetTests.class)
    public void testMakingShots(List<ShipPosition> data) {
        // Given
        Fleet fleet = new Fleet(new ShipsPositions(data));
        Board board = new DefaultBoard(new ShipsPositions(data));
        GameSettings.PlayerSettings player1Settings = new GameSettings.PlayerSettings("001", "TestPlayer1", board, fleet);
        GameSettings.PlayerSettings player2Settings = new GameSettings.PlayerSettings("002", "TestPlayer2", board, fleet);
        List<GameSettings.PlayerSettings> playerSettingsList = new ArrayList<>();
        playerSettingsList.add(player1Settings);
        playerSettingsList.add(player2Settings);
        GameSettings gameSettings = new GameSettings(playerSettingsList);
        GameRoom gameRoom = new GameRoom(gameSettings);
        // When
        SoftAssert softAssert = new SoftAssert();
        gameRoom.makeShot(1);
        gameRoom.makeShot(3);
        // Then
        softAssert.assertFalse(fleet.checkIfAllShipsUntouched());
        softAssert.assertEquals(fleet.getShipConditionByIndex(1), ShipCondition.SUNK);
        softAssert.assertEquals(board.getField(1), FieldState.ACCURATE_SHOT);
        softAssert.assertEquals(board.getField(3), FieldState.ACCURATE_SHOT);
        softAssert.assertEquals(board.getField(4), FieldState.INTACT_SHIP);
        softAssert.assertEquals(board.getField(0), FieldState.WATER);
        softAssert.assertAll();
    }
}
