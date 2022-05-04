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

    public List<ShipPosition> getAllShipsPositions() {
        return shipsPosition;
    }

    public List<Integer> getAllOccupiedFields() {
        return shipsPosition.stream()
                .flatMap(p -> p.positions().stream())
                .toList();
    }

    @Override
    public String toString() {
        return shipsPosition.toString();
    }
}
