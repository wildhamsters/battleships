package org.wildhamsters.battleships;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * @author Dominik Żebracki
 */
@Component
class DefaultBoard implements Board {

    private final ArrayList<FieldState> board;

    DefaultBoard(ArrayList<FieldState> board) {
        this.board = board;
    }

    DefaultBoard() {
        ArrayList<FieldState> list = new ArrayList<>();
        IntStream.range(0, 25).forEach(x -> list.add(FieldState.WATER));
        this.board = list;
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
        IntStream.range(0, board.size()).forEach(i -> board.set(i, FieldState.WATER));
    }

    @Override
    public BoardDimension size() {
        return new BoardDimension(0, board.size()-1);
    }
}
