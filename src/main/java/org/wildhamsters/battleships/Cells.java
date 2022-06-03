package org.wildhamsters.battleships;

import org.wildhamsters.battleships.board.FieldState;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Kevin Nowak
 */

public class Cells {
    private final Map<Integer, FieldState> cells;

    public Cells(Map<Integer, FieldState> cells) {
        if (cells == null) {
            this.cells = new HashMap<>();
        } else {
            this.cells = new HashMap<>(cells);
        }
    }
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP",
        justification = "Only used by jackson to serialize data"
    )   
    public Map<Integer, FieldState> getCells() {
        return cells;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cells cells1 = (Cells) o;
        return Objects.equals(cells, cells1.cells);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cells);
    }
}
