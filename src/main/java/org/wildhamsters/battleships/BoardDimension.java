package org.wildhamsters.battleships;

record BoardDimension(int min, int max) {
    boolean isWithinDimension(int value) {
        return value >= min && value <= max;
    }
}
