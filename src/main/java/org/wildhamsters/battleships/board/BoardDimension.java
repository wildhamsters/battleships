package org.wildhamsters.battleships.board;

/**
 * @author Dominik Żebracki
 */
public record BoardDimension(int min, int max) {
    public boolean isWithinDimension(int value) {
        return value >= min && value <= max;
    }
}
