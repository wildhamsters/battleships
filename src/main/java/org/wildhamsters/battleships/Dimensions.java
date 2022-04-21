package org.wildhamsters.battleships;

record Dimensions(int min, int max) {
    
    boolean isWithinDimensions(int value) {
        return value >= min && value <= max;
    }
}
