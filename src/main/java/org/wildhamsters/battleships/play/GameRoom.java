package org.wildhamsters.battleships.play;

import org.wildhamsters.battleships.Result;
import org.wildhamsters.battleships.configuration.GameSettings;

import java.util.UUID;

/**
 * Container for both players. Gives access to making shot and checking whether round is finished.
 *
 * @author Piotr Chowaniec
 */
public class GameRoom {

    private final String id = UUID.randomUUID().toString();
    private final Player playerOne;
    private final Player playerTwo;
    private final SingleShot singleShot;
    private final MatchStatistics matchStatistics;

    public GameRoom(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.matchStatistics = new MatchStatistics(id);
        singleShot = new SingleShot(playerOne, playerTwo, matchStatistics);
    }

    public GameRoom(GameSettings gameSettings) {
        playerOne = Player.of(gameSettings.playerSettings().get(0));
        playerTwo = Player.of(gameSettings.playerSettings().get(1));
        this.matchStatistics = new MatchStatistics(id);
        singleShot = new SingleShot(playerOne, playerTwo, matchStatistics);
    }

    /**
     * Passes cell index to currentPlayer's makeShot() method.
     *
     * @param position cell index that is shot.
     * @return proper FieldState as a result of made shot.
     */
    public Result makeShot(int position) {
        return singleShot.makeShot(position);
    }

    String obtainUUID() {
        return id;
    }
}
