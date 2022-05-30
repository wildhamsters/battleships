package org.wildhamsters.battleships;

import org.wildhamsters.battleships.board.FieldState;

import java.util.List;
import java.util.Map;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.wildhamsters.battleships.board.FieldState;

/**
 * DataTransferObject between GameRoom and GameService.
 *
 * @author Mariusz Bal
 */
@SuppressFBWarnings(
        value = "EI_EXPOSE_REP",
        justification = "Can't fix that for now"
)
public record Result(Event event,
                     Map<Integer, FieldState> cells,
                     List<Integer> shipCells,
                     Boolean finished,
                     String error,
                     String currentTurnPlayer,
                     String currentTurnPlayerName,
                     String opponent) {
}
