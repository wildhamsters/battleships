package org.wildhamsters.battleships;

import org.wildhamsters.battleships.board.FieldState;

import java.util.Map;

/**
 * DataTransferObject between GameRoom and GameService.
 *
 * @author Mariusz Bal
 */
public record Result(Event event,
                     Map<Integer, FieldState> cells,
                     Boolean finished,
                     String error,
                     String currentTurnPlayer,
                     String currentTurnPlayerName,
                     String opponent) {}
