package org.wildhamsters.battleships.board;

/**
 * @author Dominik Żebracki
 */
public enum FieldState {

    WATER("."), MISSED_SHOT(" "), ACCURATE_SHOT("X"), INTACT_SHIP("#");

    private final String representation;

    FieldState(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}
