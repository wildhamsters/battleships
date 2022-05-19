package org.wildhamsters.battleships.configuration;

import org.wildhamsters.battleships.board.Board;
import org.wildhamsters.battleships.fleet.Fleet;

import java.util.List;
import java.util.Optional;

/**
 * Game settings needed to start a game and creating {@link org.wildhamsters.battleships.play.GameRoom}.
 *
 * @author Dominik Żebracki
 */
public record GameSettings(List<PlayerSettings> playerSettings) {

    public Fleet firstPlayersFleet() {
        return playerSettings.get(0).fleet();
    }

    public Fleet secondPlayersFleet() {
        return playerSettings.get(1).fleet();
    }

    /**
     * Individual player data.
     */
    public record PlayerSettings(String id,
            String name,
            Board board,
            Fleet fleet){}
}
