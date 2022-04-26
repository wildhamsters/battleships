package org.wildhamsters.battleships;

/**
 * @author Dominik Żebracki
 */
interface Board {

    FieldState getField(int position);
    void setField(FieldState fieldState, int position);
    void clearBoard();
    BoardDimension size();
}
