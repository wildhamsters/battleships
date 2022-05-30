package org.wildhamsters.battleships;

import org.wildhamsters.battleships.board.FieldState;

import java.util.List;
import java.util.Map;

/**
 * DataTransferObject between GameRoom and GameService.
 *
 * @author Mariusz Bal
 */
@ExcludeFromJacocoGeneratedReport
public record Result(Event event,
                     Map<Integer, FieldState> cells,
                     List<Integer> shipCells,
                     Boolean finished,
                     String error,
                     String currentTurnPlayer,
                     String currentTurnPlayerName,
                     String opponent) {
}
