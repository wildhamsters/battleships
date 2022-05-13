package org.wildhamsters.battleships.board;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Test
public class DefaultBoardTest {

    @Test(dataProvider = "boardProvider")
    void shouldReturnProperFieldState(Board board, int position, FieldState expected) {
        // given
        // when
        FieldState actual = board.getField(position);
        // then
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "boardProvider")
    void testClearingWholeBoard(Board board, int notUsedInt, FieldState notUsedObject) {
        SoftAssert sa = new SoftAssert();
        board.clearBoard();
        for (int i = 0; i < board.size().max(); i++) {
            sa.assertEquals(board.getField(i), FieldState.WATER);
        }
        sa.assertAll();
    }

    @DataProvider
    Object[][] boardProvider() {
        return new Object[][] {
                { createTestBoardWithSpecificStateAtPosition(FieldState.INTACT_SHIP, 0), 0, FieldState.INTACT_SHIP },
                { createTestBoardWithSpecificStateAtPosition(FieldState.ACCURATE_SHOT, 5), 5,
                        FieldState.ACCURATE_SHOT },
                { createTestBoardWithSpecificStateAtPosition(FieldState.MISSED_SHOT, 10), 10, FieldState.MISSED_SHOT },
                { createTestBoardWithSpecificStateAtPosition(FieldState.WATER, 15), 15, FieldState.WATER }
        };
    }

    private Board createTestBoardWithSpecificStateAtPosition(FieldState fieldState, int position) {
        Board board = new DefaultBoard();
        board.setField(fieldState, position);
        return board;
    }

    @Test
    void testBoardSize() {
        Board board = new DefaultBoard();
        BoardDimension boardDimension = new BoardDimension(0, 99);
        assertEquals(board.size(), boardDimension);
    }

    @Test
    void testSettingBoardField() {
        Board board = new DefaultBoard();
        FieldState expected = FieldState.INTACT_SHIP;

        board.setField(FieldState.INTACT_SHIP, 15);
        FieldState actual = board.getField(15);

        assertEquals(actual, expected);
    }
}
