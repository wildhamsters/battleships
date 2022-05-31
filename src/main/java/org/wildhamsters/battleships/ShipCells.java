package org.wildhamsters.battleships;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * @author Kevin Nowak
 */
public class ShipCells {
    private final List<Integer> shipCells;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "No expose of internal representation in this case"
    )
    public ShipCells(List<Integer> shipCells) {
        this.shipCells = shipCells;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShipCells shipCells1 = (ShipCells) o;
        return Objects.equals(shipCells, shipCells1.shipCells);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shipCells);
    }
}
