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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

@Test
public class SingleShotTest {

    @Test(dataProvider = "notSinkingShip")
    public void shouldReturnOneFieldResult_whenShipIsNotSinking(Player playerOne, Player playerTwo, int position, Result expected) {
        // Given
        SingleShot singleShot = new SingleShot(playerOne, playerTwo);
        // When
        Result actual = singleShot.makeShot(position);
        // Then
        assertEquals(actual, expected, "Should return Result with one accurate shot, players stays the same.");
    }

    @DataProvider
    private Object[][] notSinkingShip() {
        Player playerOne = Player.of("1", "Player One", Board.create(), new Fleet());
        Player playerTwo = Player.of("2", "Player Two", prepareBoard(), prepareFleet());
        return new Object[][]{
                {playerOne, playerTwo, 1, prepareResult(new HashMap<>(Map.of(1, FieldState.ACCURATE_SHOT)),
                        "1", "Player One", "2", playerTwo, 1)},
                {playerOne, playerTwo, 62, prepareResult(new HashMap<>(Map.of(62, FieldState.ACCURATE_SHOT)),
                        "1", "Player One", "2", playerTwo, 62)}
        };
    }

    // @Test(dataProvider = "sinkingShip")
    // public void shouldReturnMultiFieldResult_whenShipIsSinking(Player playerOne, Player playerTwo, int position, Result expected) {
    //     // Given
    //     SingleShot singleShot = new SingleShot(playerOne, playerTwo);
    //     // When
    //     Result actual = singleShot.makeShot(position);
    //     // Then
    //     assertEquals(actual, expected, "Should return Result with single accurate shot field, missed fields" +
    //             "around sinking ship, player stays the same.");
    // }

    // @DataProvider
    // private Object[][] sinkingShip() {
    //     Player playerOne = Player.of("1", "Player One", Board.create(), new Fleet());
    //     Player playerTwo = Player.of("2", "Player Two", prepareBoard(), prepareFleet());
    //     return new Object[][]{
    //             {playerOne, playerTwo, 43, prepareResult(new HashMap<>(Map.of(43, FieldState.ACCURATE_SHOT,
    //                             32, FieldState.MISSED_SHOT, 33, FieldState.MISSED_SHOT, 42, FieldState.MISSED_SHOT,
    //                             52, FieldState.MISSED_SHOT, 53, FieldState.MISSED_SHOT)),
    //                     "1", "Player One", "2", playerTwo, 43)},
    //             {playerOne, playerTwo, 89, prepareResult(new HashMap<>(Map.of(89, FieldState.ACCURATE_SHOT,
    //                             58, FieldState.MISSED_SHOT, 68, FieldState.MISSED_SHOT, 88, FieldState.MISSED_SHOT,
    //                             98, FieldState.MISSED_SHOT, 99, FieldState.MISSED_SHOT)),
    //                     "1", "Player One", "2", playerTwo, 89)}
    //     };
    // }

    @Test(dataProvider = "missedShot")
    public void shouldReturnSingleFieldResult_whenMissedShot(Player playerOne, Player playerTwo, int position, Result expected) {
        // Given
        SingleShot singleShot = new SingleShot(playerOne, playerTwo);
        // When
        Result actual = singleShot.makeShot(position);
        // Then
        assertEquals(actual, expected, "Should return Result with single missed field and with switched players.");
    }

    @DataProvider
    private Object[][] missedShot() {
        Player playerOne = Player.of("1", "Player One", prepareBoard(), prepareFleet());
        Player playerTwo = Player.of("2", "Player Two", prepareBoard(), prepareFleet());
        return new Object[][]{
                {playerOne, playerTwo, 0, prepareResult(new HashMap<>(Map.of(0, FieldState.MISSED_SHOT)),
                        "2", "Player Two", "1", playerTwo, 0)},
                {playerOne, playerTwo, 9, prepareResult(new HashMap<>(Map.of(9, FieldState.MISSED_SHOT)),
                        "2", "Player Two", "1", playerTwo, 9)},
                {playerOne, playerTwo, 90, prepareResult(new HashMap<>(Map.of(90, FieldState.MISSED_SHOT)),
                        "2", "Player Two", "1", playerTwo,90)},
                {playerOne, playerTwo, 99, prepareResult(new HashMap<>(Map.of(99, FieldState.MISSED_SHOT)),
                        "2", "Player Two", "1", playerTwo, 99)}
        };
    }

    private Board prepareBoard() {
        Board board = Board.create();
        board.setField(FieldState.INTACT_SHIP, 1);
        board.setField(FieldState.INTACT_SHIP, 2);
        board.setField(FieldState.INTACT_SHIP, 43);
        board.setField(FieldState.MISSED_SHOT, 34);
        board.setField(FieldState.MISSED_SHOT, 35);
        board.setField(FieldState.MISSED_SHOT, 36);
        board.setField(FieldState.MISSED_SHOT, 44);
        board.setField(FieldState.ACCURATE_SHOT, 45);
        board.setField(FieldState.MISSED_SHOT, 46);
        board.setField(FieldState.MISSED_SHOT, 54);
        board.setField(FieldState.MISSED_SHOT, 55);
        board.setField(FieldState.MISSED_SHOT, 56);
        board.setField(FieldState.ACCURATE_SHOT, 61);
        board.setField(FieldState.INTACT_SHIP, 62);
        board.setField(FieldState.INTACT_SHIP, 63);
        board.setField(FieldState.MISSED_SHOT, 59);
        board.setField(FieldState.MISSED_SHOT, 78);
        board.setField(FieldState.ACCURATE_SHOT, 69);
        board.setField(FieldState.ACCURATE_SHOT, 79);
        board.setField(FieldState.INTACT_SHIP, 89);
        return board;
    }

    private Fleet prepareFleet() {
        List<ShipPosition> ships = new ArrayList<>() {{
            add(new ShipPosition(List.of(1, 2)));
            add(new ShipPosition(List.of(43)));
            add(new ShipPosition(List.of(45)));
            add(new ShipPosition(List.of(61, 62, 63)));
            add(new ShipPosition(List.of(69, 79, 89)));
        }};
        Fleet fleet = new Fleet(new ShipsPositions(ships));
        fleet.makeShot(45);
        fleet.makeShot(61);
        fleet.makeShot(69);
        fleet.makeShot(79);
        return fleet;
    }

    private Result prepareResult(Map<Integer, FieldState> fieldMap,
                                 String currentTurnPlayer,
                                 String currentTurnPlayerName,
                                 String opponent,
                                 Player player, int position) {

        List<Integer> sunkenShipList = (player.getSunkShipPositions(position).size() > 1) ? player.getSunkShipPositions(position) : null;
        return new Result(
                Event.GAMEPLAY, fieldMap,sunkenShipList, false, "", currentTurnPlayer, currentTurnPlayerName, opponent);
    }
}
