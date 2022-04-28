package org.wildhamsters.battleships.fleet;

import java.util.List;

/**
 * @author Kevin Nowak
 */
public record ShipPosition(List<Integer> positions) {

    int shipSize() {
        return positions().size();
    }

    @Override
    public String toString() {
        return positions.toString();
    }
}
