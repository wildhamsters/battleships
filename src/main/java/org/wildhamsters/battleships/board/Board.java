package org.wildhamsters.battleships.board;

import org.wildhamsters.battleships.fleet.ShipsPositions;

//TODO whole content of the package should be probably moved to play and refactored
/**
 * @author Dominik Żebracki
 */
public interface Board {
    static Board create() {
        return new DefaultBoard();
    }

    static Board create(ShipsPositions shipsPositions) {
        return new DefaultBoard(shipsPositions);
    }

    FieldState getField(int position);
    void setField(FieldState fieldState, int position);
    void clearBoard();
    BoardDimension size();
}
