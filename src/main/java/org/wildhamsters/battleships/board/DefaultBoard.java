package org.wildhamsters.battleships.board;

import org.springframework.stereotype.Component;
import org.wildhamsters.battleships.fleet.ShipsPositions;

import java.util.ArrayList;
import java.util.stream.IntStream;

//TODO board is hardcoded to be of size 100. Change it whole application supports it.
// Change representation to map.
/**
 * @author Dominik Żebracki
 */
@Component
class DefaultBoard implements Board {

    private final ArrayList<FieldState> board;

    DefaultBoard(ArrayList<FieldState> board) {
        this.board = board;
    }

    public DefaultBoard(ShipsPositions shipsPositions) {
        ArrayList<FieldState> list = new ArrayList<>();
        IntStream.range(0, 100).forEach(x -> list.add(FieldState.WATER));
        board = list;
        fillBoardWithShips(shipsPositions);
    }

    DefaultBoard() {
        ArrayList<FieldState> list = new ArrayList<>();
        IntStream.range(0, 100).forEach(x -> list.add(FieldState.WATER));
        this.board = list;
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
        IntStream.range(0, board.size()).forEach(i -> board.set(i, FieldState.WATER));
    }

    @Override
    public BoardDimension size() {
        return new BoardDimension(0, board.size() - 1);
    }

    private void fillBoardWithShips(ShipsPositions shipsPositions) {
        shipsPositions.getAllShipsPositions()
                .stream()
                .flatMap(p -> p.positions().stream())
                .forEach(i -> board.set(i, FieldState.INTACT_SHIP));
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        IntStream.range(1, board.size()+1)
                .forEach(i -> {
                    sb.append(board.get(i - 1).toString()).append(" ");
                    if(i % 10 == 0) {
                        sb.append(System.lineSeparator());
                    }
                });
        return sb.toString();
    }
}
