package org.wildhamsters.battleships;

import java.util.List;
import java.util.Map;

import org.wildhamsters.battleships.board.FieldState;

/**
 * DataTransferObject between GameRoom and GameService.
 *
 * @author Mariusz Bal
 */
public record Result(Event event,
        Map<Integer, FieldState> cells,
        List<Integer> shipCells,
        Boolean finished,
        String error,
        String currentTurnPlayer,
        String currentTurnPlayerName,
        String opponent) {
}
