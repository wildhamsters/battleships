package org.wildhamsters.battleships;

import java.util.ArrayList;

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
    public Dimensions size() {
        return new Dimensions(0, board.size()-1);
    }
}
