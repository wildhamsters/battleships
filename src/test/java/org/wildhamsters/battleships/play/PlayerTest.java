package org.wildhamsters.battleships.play;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.wildhamsters.battleships.board.Board;
import org.wildhamsters.battleships.board.FieldState;
import org.wildhamsters.battleships.fleet.Fleet;
import org.wildhamsters.battleships.fleet.ShipPosition;
import org.wildhamsters.battleships.fleet.ShipsPositions;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

@Test
public class PlayerTest {

    @Test(dataProvider = "testBoardForInRangeShots")
    public void shouldReturnProperFieldState_whenShotIsInBoard(Board board, int position, FieldState expected) {
        Player player = createPlayer(board);
        assertEquals(player.enemyShotResult(position), expected,
                "Returned FieldState is not appropriate to original cell value.");
    }

    @DataProvider
    private Object[][] testBoardForInRangeShots() {
        return new Object[][]{
                {createBoardWithShipAtPosition(5), 5, FieldState.ACCURATE_SHOT},
                {createBoardWithShipAtPosition(0), 0, FieldState.ACCURATE_SHOT},
                {createBoardWithShipAtPosition(1), 1, FieldState.ACCURATE_SHOT},
                {createBoardWithShipAtPosition(23), 23, FieldState.ACCURATE_SHOT},
                {createBoardWithShipAtPosition(24), 24, FieldState.ACCURATE_SHOT},
                {createBoardWithShipAtPosition(5), 6, FieldState.MISSED_SHOT},
                {createBoardWithShipAtPosition(5), 0, FieldState.MISSED_SHOT},
                {createBoardWithShipAtPosition(5), 1, FieldState.MISSED_SHOT},
                {createBoardWithShipAtPosition(5), 23, FieldState.MISSED_SHOT},
                {createBoardWithShipAtPosition(5), 24, FieldState.MISSED_SHOT}
        };
    }

    @Test(dataProvider = "testBoardForOutOfRangeShots", expectedExceptions = IllegalShotException.class)
    public void shouldThrowException_whenShotIsOutOfBoard(Board board, int position) {
        Player player = createPlayer(board);
        player.enemyShotResult(position);
    }

    @DataProvider
    private Object[][] testBoardForOutOfRangeShots() {
        return new Object[][]{
                {Board.create(), -1},
                {Board.create(), 100},
        };
    }

    private Board createBoardWithShipAtPosition(int position) {
        Board board = Board.create();
        board.setField(FieldState.INTACT_SHIP, position);
        return board;
    }

    public void shouldReturnTrue_whenFleetIsSunk() {
        Player player = createPlayer(Board.create());
        player.takeShot(1, FieldState.ACCURATE_SHOT);
        player.takeShot(2, FieldState.ACCURATE_SHOT);
        player.takeShot(43, FieldState.ACCURATE_SHOT);
        assertTrue(player.isLost(), "Should return true when all ships are hit.");
    }

    public void shouldReturnFalse_whenFleetIsNotSunk() {
        Player player = createPlayer(Board.create());
        player.takeShot(1, FieldState.ACCURATE_SHOT);
        player.takeShot(43, FieldState.ACCURATE_SHOT);
        assertFalse(player.isLost(), "Should return false when not all ships are hit.");
    }

    private Player createPlayer(Board board) {
        return Player.of("1", "", board, createFleet());
    }

    private Fleet createFleet() {
        List<ShipPosition> ships = new ArrayList<>() {{
            add(new ShipPosition(List.of(1, 2)));
            add(new ShipPosition(List.of(43)));
        }};
        // return new Fleet(new ShipsPositions(ships));
        return new Fleet();
    }
}
