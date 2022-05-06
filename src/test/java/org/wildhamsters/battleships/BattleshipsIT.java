package org.wildhamsters.battleships;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.wildhamsters.battleships.board.Board;
import org.wildhamsters.battleships.board.FieldState;
import org.wildhamsters.battleships.configuration.GameSettings;
import org.wildhamsters.battleships.fleet.*;
import org.wildhamsters.battleships.play.GameRoom;

import java.util.ArrayList;
import java.util.List;

public class BattleshipsIT {

    /**
     * Players making shots.
     *
     * Given: Fleet, Board, GameSettings, GameRoom
     * When: Shots made via gameRoom.makeShot(int position)
     * Then: Resulting changes of those shots should be seen on Board and Fleet
     *
     */
    @Test(dataProvider = "fieldList-provider", dataProviderClass = FleetTests.class)
    public void testMakingShots(List<ShipPosition> data) {
        // Given
        Fleet fleet = new Fleet(new ShipsPositions(data));
        Board board = Board.create(new ShipsPositions(data));
        GameSettings.PlayerSettings player1Settings = new GameSettings.PlayerSettings(
                "001",
                "TestPlayer1",
                board,
                fleet
        );
        GameSettings.PlayerSettings player2Settings = new GameSettings.PlayerSettings(
                "002",
                "TestPlayer2",
                board,
                fleet
        );
        List<GameSettings.PlayerSettings> playerSettingsList = new ArrayList<>();
        playerSettingsList.add(player1Settings);
        playerSettingsList.add(player2Settings);
        GameSettings gameSettings = new GameSettings(playerSettingsList);
        GameRoom gameRoom = new GameRoom(gameSettings);
        Result result = new Result(
                Event.GAMEPLAY,
                4,
                FieldState.ACCURATE_SHOT,
                false,
                "",
                "001",
                "TestPlayer1",
                "002"
        );
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
        softAssert.assertEquals(gameRoom.makeShot(4), result);

        for (int i = 0; i < 99; i++) {
            gameRoom.makeShot(i);
        }

        Result resultFinished = new Result(
                Event.GAMEPLAY,
                99,
                FieldState.ACCURATE_SHOT,
                true,
                "",
                "001",
                "TestPlayer1",
                "002"
        );
        softAssert.assertEquals(gameRoom.makeShot(99), resultFinished);
        softAssert.assertAll();
    }
}
