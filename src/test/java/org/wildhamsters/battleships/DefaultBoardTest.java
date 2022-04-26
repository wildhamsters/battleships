package org.wildhamsters.battleships;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.testng.Assert.assertEquals;

@Test
public class DefaultBoardTest {

    @Test(dataProvider = "boardProvider")
    void shouldReturnProperFieldState(ArrayList<FieldState> board, int position, FieldState expected) {
        //given
        var testBoard = new DefaultBoard(board);
        //when
        var actual = testBoard.getField(position);
        //then
        assertEquals(actual, expected);
    }

    @DataProvider
    Object[][] boardProvider() {
        return new Object[][]{
                {new ArrayList<>(List.of(FieldState.WATER)), 0, FieldState.WATER},
                {setField(createBoardWithWater(10), 2, FieldState.INTACT_SHIP), 2, FieldState.INTACT_SHIP},
                {setField(createBoardWithWater(2), 1, FieldState.ACCURATE_SHOT), 1, FieldState.ACCURATE_SHOT},
                {setField(createBoardWithWater(13), 6, FieldState.MISSED_SHOT), 6, FieldState.MISSED_SHOT},
                {setField(createBoardWithWater(123), 45, FieldState.MISSED_SHOT), 45, FieldState.MISSED_SHOT},
                {setField(createBoardWithWater(14), 1, FieldState.MISSED_SHOT), 1, FieldState.MISSED_SHOT},
                {setField(createBoardWithWater(89), 0, FieldState.MISSED_SHOT), 0, FieldState.MISSED_SHOT},
        };
    }

    private ArrayList<FieldState> createBoardWithWater(int size) {
        var board = new ArrayList<FieldState>(size);
        IntStream.range(0, size)
                .forEach(i -> board.add(i, FieldState.WATER));
        return board;
    }

    private ArrayList<FieldState> setField(ArrayList<FieldState> board, int position, FieldState fieldState) {
        board.set(position, fieldState);
        return board;
    }
}
