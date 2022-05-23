package org.wildhamsters.battleships;


import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.wildhamsters.battleships.board.Board;
import org.wildhamsters.battleships.board.FieldState;
import org.wildhamsters.battleships.configuration.GameSettings;
import org.wildhamsters.battleships.fleet.*;
import org.wildhamsters.battleships.play.GameRoom;

import java.util.List;

public class BattleshipsIT {
    
    @Test(dataProvider = "fieldListProvider", dataProviderClass = FleetTests.class)
    public void testMakingShotsAndVerifyThatBoardAndFleetProcessShotCorrectly(List<ShipPosition> data) {
        // Given
        Fleet testFleet = new Fleet(new ShipsPositions(data));
        Board testBoard = Board.create(new ShipsPositions(data));
        GameRoom testRoom = createDummyGameRoom(testFleet, testBoard);
        SoftAssert softAssert = new SoftAssert();
        // When
        testRoom.makeShot(1);
        testRoom.makeShot(3);
        testRoom.makeShot(95);
        // Then
        softAssert.assertFalse(testFleet.checkIfAllShipsUntouched());
        softAssert.assertEquals(testFleet.getShipConditionByIndex(1), ShipCondition.SUNK);
        softAssert.assertEquals(testFleet.getShipConditionByIndex(3), ShipCondition.HIT);
        softAssert.assertEquals(testFleet.getShipConditionByIndex(4), ShipCondition.HIT);
        softAssert.assertEquals(testFleet.getShipConditionByIndex(99), ShipCondition.UNTOUCHED);
        softAssert.assertEquals(testFleet.getShipConditionByIndex(95), ShipCondition.NO_SHIP_HERE);

        softAssert.assertEquals(testBoard.getField(1), FieldState.ACCURATE_SHOT);
        softAssert.assertEquals(testBoard.getField(3), FieldState.ACCURATE_SHOT);
        softAssert.assertEquals(testBoard.getField(4), FieldState.INTACT_SHIP);
        softAssert.assertEquals(testBoard.getField(99), FieldState.INTACT_SHIP);
        softAssert.assertEquals(testBoard.getField(95), FieldState.MISSED_SHOT);

        softAssert.assertAll();
    }

    static GameRoom createDummyGameRoom(Fleet fleet, Board board) {
        GameSettings.PlayerSettings ps1 = new GameSettings.PlayerSettings(
                "001",
                "Player1",
                board,
                fleet
        );

        GameSettings.PlayerSettings ps2 = new GameSettings.PlayerSettings(
                "002",
                "Player2",
                board,
                fleet
        );

        GameSettings settings = new GameSettings(List.of(ps1, ps2));

        return new GameRoom(settings);
    }

}
