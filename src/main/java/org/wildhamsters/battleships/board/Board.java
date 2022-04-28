package org.wildhamsters.battleships.board;

/**
 * @author Dominik Żebracki
 */
public interface Board {
    static Board create() {
        return new DefaultBoard();
    }

    FieldState getField(int position);
    void setField(FieldState fieldState, int position);
    void clearBoard();
    BoardDimension size();
}
