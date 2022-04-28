package org.wildhamsters.battleships;

import java.util.List;

/**
 * @author Dominik Żebracki
 */
record ConnectionStatus(String message,
                        String playerOneSessionId,
                        List<Integer> playerOneShipPositions,
                        String playerTwoSessionId,
                        List<Integer> playerTwoShipPositions,
                        String startingPlayerName,
                        Event event) {
}
