package org.wildhamsters.battleships;

import java.util.List;

/**
 * @author Dominik Żebracki
 */
class ShipsPosition {

    private final List<List<Integer>> shipsPosition;

    ShipsPosition(List<List<Integer>> shipsPosition) {
        this.shipsPosition = shipsPosition;
    }

    List<List<Integer>> getPositionsOfShipsOfSize(int shipSize) {
        return shipsPosition.stream()
                .filter(l -> l.size() == shipSize)
                .toList();
    }

    List<List<Integer>> getAllShipsPositions() {
        return shipsPosition;
    }
}
