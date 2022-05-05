package org.wildhamsters.battleships;

import java.util.List;

/**
 * Data send to players after game creation.
 *
 * @author Dominik Żebracki
 */
record ConnectionStatus(String message,
                        String playerOneSessionId,
                        List<Integer> playerOneShipPositions,
                        String playerTwoSessionId,
                        List<Integer> playerTwoShipPositions,
                        String startingPlayerName,
                        String playerOneName,
                        String playerTwoName,
                        Event event) {
}
