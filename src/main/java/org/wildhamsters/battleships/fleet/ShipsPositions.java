package org.wildhamsters.battleships.fleet;

import java.util.List;

/**
 * @author Dominik Żebracki
 */
public class ShipsPositions {

    private final List<ShipPosition> shipsPosition;

    public ShipsPositions(List<ShipPosition> shipsPosition) {
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

    @Override
    public String toString() {
        return shipsPosition.toString();
    }
}
