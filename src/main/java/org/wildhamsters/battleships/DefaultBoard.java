package org.wildhamsters.battleships;

import java.util.ArrayList;

/**
 * @author Dominik Żebracki
 */
class DefaultBoard implements Board {

    private final ArrayList<FieldState> board;

    DefaultBoard(ArrayList<FieldState> board) {
        this.board = board;
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
    public BoardDimension size() {
        return new BoardDimension(0, board.size()-1);
    }
}
