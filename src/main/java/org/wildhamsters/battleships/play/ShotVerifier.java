package org.wildhamsters.battleships.play;

import org.wildhamsters.battleships.board.Board;
import org.wildhamsters.battleships.board.FieldState;

class ShotVerifier {

    FieldState verifyShot(int position, Board board) throws IllegalShotException {
        if (invalidShot(position, board)) {
            throw new IllegalShotException();
        }
        return switch (board.getField(position)) {
            case WATER -> FieldState.MISSED_SHOT;
            case INTACT_SHIP -> FieldState.ACCURATE_SHOT;
            default -> throw new IllegalShotException();
        };
    }

    private boolean invalidShot(int position, Board board) {
        return !board.size().isWithinDimension(position) || alreadyShot(position, board);
    }

    private boolean alreadyShot(int position, Board board) {
        return switch (board.getField(position)) {
            case WATER, INTACT_SHIP -> false;
            case ACCURATE_SHOT, MISSED_SHOT -> true;
        };
    }
}
