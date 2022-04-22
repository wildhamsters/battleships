package org.wildhamsters.battleships;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.testng.Assert.*;

public class ShootVerifierTest {

    @Test(dataProvider = "data")
    public void testValidShoot(ShootVerifier verifier, Board board) throws IllegalShootException {
        FieldState actual = verifier.verifyShoot(15, board);
        FieldState expected = FieldState.MISSED_SHOT;
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "data", expectedExceptions = IllegalShootException.class)
    public void testAlreadyShotOnField(ShootVerifier verifier, Board board) throws IllegalShootException {
        board.setField(FieldState.ACCURATE_SHOT, 15);
        verifier.verifyShoot(15, board);
    }

    @Test(dataProvider = "data", expectedExceptions = IllegalShootException.class)
    public void testAlreadyMissedField(ShootVerifier verifier, Board board) throws IllegalShootException {
        board.setField(FieldState.MISSED_SHOT, 10);
        verifier.verifyShoot(10, board);
    }

    @Test(dataProvider = "data", expectedExceptions = IllegalShootException.class)
    public void testOutOfBoundsShoot(ShootVerifier verifier, Board board) throws IllegalShootException {
        verifier.verifyShoot(30, board);
    }

    @DataProvider
    Object[][] data() {
        return new Object[][]{
                {new ShootVerifier(new BoardDimension(0, 25)), new DummyBoard(25)}
        };
    }

    private class DummyBoard implements Board {
        List<FieldState> board;

        DummyBoard(int size) {
            board = new ArrayList<>();
            IntStream.range(0, size).forEach(x -> board.add(FieldState.WATER));
        }

        @Override
        public FieldState getFiled(int position) {
            return board.get(position);
        }

        @Override
        public void setField(FieldState fieldState, int position) {
            board.set(position, fieldState);
        }

        @Override
        public void clearBoard() {
        }

        @Override
        public BoardDimension size() {
            return new BoardDimension(0, board.size());
        }
    }
}
