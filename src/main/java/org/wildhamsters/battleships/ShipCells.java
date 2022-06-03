package org.wildhamsters.battleships;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * @author Kevin Nowak
 */

public class ShipCells {
    private final List<Integer> shipCells;

    public ShipCells(List<Integer> shipCells) {
        if (shipCells == null) {
            this.shipCells = new ArrayList<>();
        } else {
            this.shipCells = new ArrayList<>(shipCells);
        }
    }

    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP", 
        justification = "Only used by jackson to serialize data"
    )
    public List<Integer> getShipCells() {
        return shipCells;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ShipCells shipCells1 = (ShipCells) o;
        return Objects.equals(shipCells, shipCells1.shipCells);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shipCells);
    }
}
