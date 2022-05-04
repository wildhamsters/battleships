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

    public Optional<Fleet> firstPlayersFleet() {
        return !playerSettings.isEmpty() ? Optional.of(playerSettings.get(0).fleet()) : Optional.empty();
    }

    public Optional<Fleet> secondPlayersFleet() {
        return playerSettings.size() > 1 ? Optional.of(playerSettings.get(1).fleet) : Optional.empty();
    }

    /**
     * Individual player data.
     */
    public record PlayerSettings(String id,
            String name,
            Board board,
            Fleet fleet){}
}
