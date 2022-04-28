package org.wildhamsters.battleships;

import java.util.List;

/**
 * @author Kevin Nowak
 */
record ShipPosition(List<Integer> positions) {

    int shipSize() {
        return positions().size();
    }

    @Override
    public String toString() {
        return positions.toString();
    }
}
