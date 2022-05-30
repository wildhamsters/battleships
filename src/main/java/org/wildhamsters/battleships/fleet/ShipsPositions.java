package org.wildhamsters.battleships.fleet;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.io.Serializable;
import java.util.List;

/**
 * @author Dominik Żebracki
 */
@SuppressFBWarnings(
        value = {"EI_EXPOSE_REP", "SE_BAD_FIELD"},
        justification = "Can't fix that for now"
)
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
