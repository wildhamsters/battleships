package org.wildhamsters.battleships.play;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.wildhamsters.battleships.board.Board;
import org.wildhamsters.battleships.board.FieldState;
import org.wildhamsters.battleships.fleet.Fleet;
import org.wildhamsters.battleships.fleet.ShipPosition;
import org.wildhamsters.battleships.fleet.ShipsPositions;

import java.util.*;

import static org.testng.Assert.assertEquals;

@Test
public class SunkShipMarkerTest {

    @Test(dataProvider = "sinkingShip")
    public void shouldReturnSetOfFieldsWithWaterAroundShip(Set<Integer> expected, int position, Board board, Fleet fleet) {
        // Given : When
        var actual = new SunkShipMarker().fieldsToMark(fleet.getSinkingShipPosition(position), board);
        // Then
        assertEquals(actual, expected);
    }

    @DataProvider
    private Object[][] sinkingShip() {
        Board board = createBoard();
        Fleet fleet = prepareFleet();
        return new Object[][]{
                {new HashSet<>(Set.of(0, 3, 10, 11, 12, 13)), 1, board, fleet},
                {new HashSet<>(Set.of(0, 3, 10, 11, 12, 13)), 2, board, fleet},
                {new HashSet<>(Set.of(32, 33, 34, 42, 44, 52, 53, 54)), 43, board, fleet},
                {new HashSet<>(Set.of(58, 68, 88, 98, 99)), 89, board, fleet}
        };
    }

    private Board createBoard() {
        Board board = Board.create();
        board.setField(FieldState.ACCURATE_SHOT, 1);
        board.setField(FieldState.ACCURATE_SHOT, 2);
        board.setField(FieldState.ACCURATE_SHOT, 43);
        board.setField(FieldState.MISSED_SHOT, 59);
        board.setField(FieldState.MISSED_SHOT, 78);
        board.setField(FieldState.ACCURATE_SHOT, 69);
        board.setField(FieldState.ACCURATE_SHOT, 79);
        board.setField(FieldState.ACCURATE_SHOT, 89);
        return board;
    }


    private Fleet prepareFleet() {
        List<ShipPosition> ships = new ArrayList<>() {{
            add(new ShipPosition(List.of(1, 2)));
            add(new ShipPosition(List.of(43)));
            add(new ShipPosition(List.of(69, 79, 89)));
        }};
        Fleet fleet = new Fleet(new ShipsPositions(ships));
        fleet.makeShot(1);
        fleet.makeShot(2);
        fleet.makeShot(43);
        fleet.makeShot(69);
        fleet.makeShot(79);
        fleet.makeShot(89);
        return fleet;
    }
}
