package org.wildhamsters.battleships.board;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class BoardDimensionTest {

    @Test(dataProvider = "positionNotWithinRangeProvider")
    void shouldReturnFalse_whenPositionIsNotWithinDimensions(BoardDimension dimension, int position) {
        assertFalse(dimension.isWithinDimension(position));
    }

    @DataProvider
    Object[][] positionNotWithinRangeProvider() {
        return new Object[][] {
                { new BoardDimension(-9, 9), -10 },
                { new BoardDimension(-9, 9), 10 },
                { new BoardDimension(0, 10), -10 },
                { new BoardDimension(0, 10), 11 },
                { new BoardDimension(0, 0), 1 },
                { new BoardDimension(0, 0), -1 },
                { new BoardDimension(0, 99), 100 },
                { new BoardDimension(0, 100), 203 },
                { new BoardDimension(0, 453), 454 }
        };
    }

    @Test(dataProvider = "positionWithinRangeProvider")
    void shouldReturnTrue_whenPositionIsWithinDimensions(BoardDimension dimension, int position) {
        assertTrue(dimension.isWithinDimension(position));
    }

    @DataProvider
    Object[][] positionWithinRangeProvider() {
        return new Object[][] {
                { new BoardDimension(-9, 9), 9 },
                { new BoardDimension(-9, 9), -9 },
                { new BoardDimension(0, 10), 10 },
                { new BoardDimension(0, 10), 0 },
                { new BoardDimension(0, 0), 0 },
                { new BoardDimension(0, 99), 99 },
                { new BoardDimension(0, 100), 54 },
                { new BoardDimension(0, 453), 400 }
        };
    }
}
