package org.wildhamsters.battleships;

import java.util.List;

/**
 * @author Dominik Żebracki
 */
class ShipsPositions {

    private final List<ShipPosition> shipsPosition;

    ShipsPositions(List<ShipPosition> shipsPosition) {
        this.shipsPosition = shipsPosition;
    }

    List<ShipPosition> getPositionsOfShipsOfSize(int shipSize) {
        return shipsPosition.stream()
                .filter(l -> l.shipSize() == shipSize)
                .toList();
    }

    List<ShipPosition> getAllShipsPositions() {
        return shipsPosition;
    }
}
