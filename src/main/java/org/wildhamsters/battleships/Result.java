package org.wildhamsters.battleships;

import org.wildhamsters.battleships.board.FieldState;
import java.util.List;
import java.util.Map;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;



/**
 * DataTransferObject between GameRoom and GameService.
 *
 * @author Mariusz Bal
 */
public record Result(Event event,
                     Cells cells,
                     ShipCells shipCells,
                     Boolean finished,
                     String error,
                     String currentTurnPlayer,
                     String currentTurnPlayerName,
                     String opponent) {
}
