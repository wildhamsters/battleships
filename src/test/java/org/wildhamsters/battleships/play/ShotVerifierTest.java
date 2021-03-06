package org.wildhamsters.battleships.play;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.wildhamsters.battleships.board.Board;
import org.wildhamsters.battleships.board.BoardDimension;
import org.wildhamsters.battleships.board.FieldState;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.testng.Assert.assertEquals;

public class ShotVerifierTest {

    @Test(dataProvider = "data")
    public void testValidShoot(ShotVerifier verifier, Board board) throws IllegalShotException {
        FieldState actual = verifier.verifyShot(15, board);
        FieldState expected = FieldState.MISSED_SHOT;
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "data", expectedExceptions = IllegalShotException.class)
    public void testAlreadyShotOnField(ShotVerifier verifier, Board board) throws IllegalShotException {
        board.setField(FieldState.ACCURATE_SHOT, 15);
        verifier.verifyShot(15, board);
    }

    @Test(dataProvider = "data", expectedExceptions = IllegalShotException.class)
    public void testAlreadyMissedField(ShotVerifier verifier, Board board) throws IllegalShotException {
        board.setField(FieldState.MISSED_SHOT, 10);
        verifier.verifyShot(10, board);
    }

    @Test(dataProvider = "data", expectedExceptions = IllegalShotException.class)
    public void testOutOfBoundsShoot(ShotVerifier verifier, Board board) throws IllegalShotException {
        verifier.verifyShot(30, board);
    }

    @DataProvider
    Object[][] data() {
        return new Object[][]{
                {new ShotVerifier(), new DummyBoard(25)}
        };
    }

    private static class DummyBoard implements Board {
        List<FieldState> board;

        DummyBoard(int size) {
            board = new ArrayList<>();
            IntStream.range(0, size).forEach(x -> board.add(FieldState.WATER));
        }

        @Override
        public FieldState getField(int position) {
            return board.get(position);
        }

        @Override
        public void setField(FieldState fieldState, int position) {
            board.set(position, fieldState);
        }

        @Override
        public void clearBoard() {
            // left empty because never called
        }

        @Override
        public BoardDimension size() {
            return new BoardDimension(0, board.size());
        }

        @Override
        public ArrayList<FieldState> getList() {
            return null;
        }
    }
}
