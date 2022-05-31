package org.wildhamsters.battleships;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.wildhamsters.battleships.board.FieldState;
import java.util.Map;
import java.util.Objects;

/**
 * @author Kevin Nowak
 */
public class Cells {
    private final Map<Integer, FieldState> cells;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "No expose of internal representation in this case"
    )
    public Cells(Map<Integer, FieldState> cells) {
        this.cells = cells;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cells cells1 = (Cells) o;
        return Objects.equals(cells, cells1.cells);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cells);
    }
}
