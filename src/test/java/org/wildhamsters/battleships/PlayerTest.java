package org.wildhamsters.battleships;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
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
        Player player = Player.of(1, board);
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
        Player player = Player.of(1, board);
        player.enemyShotResult(position);
    }

    @DataProvider
    private Object[][] testBoardForOutOfRangeShots() {
        return new Object[][]{
                {new DefaultBoard(), -1},
                {new DefaultBoard(), 25},
        };
    }

    private Board createBoardWithShipAtPosition(int position) {
        Board board = new DefaultBoard();
        board.setField(FieldState.INTACT_SHIP, position);
        return board;
    }

    @Test(dataProvider = "fleetProvider")
    public void shouldReturnTrue_whenFleetIsSunk(ArrayList<ShipPosition> data) {
        Board board = new DefaultBoard();
        Player player = new Player(1, board, new Fleet(new ShipsPositions(data)), new ShotVerifier(board.size()));
        player.enemyShotResult(1);
        player.enemyShotResult(2);
        player.enemyShotResult(11);
        assertTrue(player.isLost(), "Should return true when all ships are hit.");
    }

    @Test(dataProvider = "fleetProvider")
    public void shouldReturnFalse_whenFleetIsNotSunk(ArrayList<ShipPosition> data) {
        Board board = new DefaultBoard();
        Player player = new Player(1, board, new Fleet(new ShipsPositions(data)), new ShotVerifier(board.size()));
        player.enemyShotResult(1);
        player.enemyShotResult(11);
        assertFalse(player.isLost(), "Should return false when not all ships are hit.");
    }
    @DataProvider
    public Object[][] fleetProvider() {
        return new Object[][]{
                {
                        new ArrayList<>() {{
                            add(new ShipPosition(List.of(1,2)));
                            add(new ShipPosition(List.of(11)));
                        }}
                }
        };
    }
}
