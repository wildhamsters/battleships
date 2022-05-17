package org.wildhamsters.battleships.fleet;

import java.io.Serializable;
import java.util.List;

/**
 * @author Dominik Żebracki
 */
public class ShipsPositions implements Serializable {

    private List<ShipPosition> shipsPosition;

    public ShipsPositions() {
    }

    public ShipsPositions(List<ShipPosition> shipsPosition) {
        this.shipsPosition = shipsPosition;
    }

    public List<ShipPosition> getShipsPosition() {
        return shipsPosition;
    }

    public void setShipsPosition(List<ShipPosition> shipsPosition) {
        this.shipsPosition = shipsPosition;
    }

    @Override
    public String toString() {
        return shipsPosition.toString();
    }
}
