package org.wildhamsters.battleships;

/**
 * @author Dominik Żebracki
 */
record BoardDimension(int min, int max) {
    boolean isWithinDimension(int value) {
        return value >= min && value <= max;
    }
}
