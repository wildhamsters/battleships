package org.wildhamsters.battleships.play;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.wildhamsters.battleships.Event;
import org.wildhamsters.battleships.Result;
import org.wildhamsters.battleships.board.Board;
import org.wildhamsters.battleships.board.FieldState;
import org.wildhamsters.battleships.fleet.Fleet;
import org.wildhamsters.battleships.fleet.ShipPosition;
import org.wildhamsters.battleships.fleet.ShipsPositions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

@Test
public class GameRoomTest {

    @Test(dataProvider = "resultDtoForGameRoom")
    public void shouldReturnResultDtoWithProperData(GameRoom gameRoom, Result expected, int cell) {
        // Given : When
        Result actual = gameRoom.makeShot(cell);
        // // Then
        assertEquals(actual, expected);
    }

    @DataProvider
    private Object[][] resultDtoForGameRoom() {
        return new Object[][]{
                {prepareGameRoom(), prepareResult(0, FieldState.MISSED_SHOT, "2", "Player Two", "1"), 0},
                {prepareGameRoom(), prepareResult(1, FieldState.ACCURATE_SHOT, "1", "Player One", "2"), 1},
                {prepareGameRoom(), prepareResult(43, FieldState.ACCURATE_SHOT, "1", "Player One", "2"), 43},
                {prepareGameRoom(), prepareResult(14, FieldState.MISSED_SHOT, "2", "Player Two", "1"), 14},
                {prepareGameRoom(), prepareResult(23, FieldState.MISSED_SHOT, "2", "Player Two", "1"), 23},
                {prepareGameRoom(), prepareResult(24, FieldState.MISSED_SHOT, "2", "Player Two", "1"), 24},
        };
    }

    private GameRoom prepareGameRoom() {
        return new GameRoom(
                Player.of("1", "Player One", createBoard(), prepareFleet()),
                Player.of("2", "Player Two", createBoard(), prepareFleet()));
    }

    private Board createBoard() {
        Board board = Board.create();
        board.setField(FieldState.INTACT_SHIP, 1);
        board.setField(FieldState.INTACT_SHIP, 2);
        board.setField(FieldState.INTACT_SHIP, 43);
        board.setField(FieldState.INTACT_SHIP, 44);
        return board;
    }

    private Result prepareResult(int cell, FieldState newState, String id, String name, String opponent) {
        return new Result(Event.GAMEPLAY, Map.of(cell, newState), null, false, "", id, name, opponent);
    }

    private Fleet prepareFleet() {
        List<ShipPosition> ships = new ArrayList<>() {
            {
                add(new ShipPosition(List.of(1, 2)));
                add(new ShipPosition(List.of(43, 44)));
            }
        };
        return new Fleet(new ShipsPositions(ships));
    }
}
