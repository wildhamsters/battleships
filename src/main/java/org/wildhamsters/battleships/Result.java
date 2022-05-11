package org.wildhamsters.battleships;

import org.wildhamsters.battleships.board.FieldState;

/**
 * DataTransferObject between GameRoom and GameService.
 *
 * @author Mariusz Bal
 */
public record Result(Event event,
                     int cell,
                     FieldState updatedState,
                     Boolean finished,
                     String error,
                     String currentTurnPlayer,
                     String currentTurnPlayerName,
                     String opponent) {}
