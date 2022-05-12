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

    GameRoom(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        MatchStatistics matchStatistics = new MatchStatistics(id);
        MatchResult matchResult = new MatchResult(id, playerOne, playerTwo);
        singleShot = new SingleShot(playerOne, playerTwo, matchStatistics, matchResult);
    }

    public GameRoom(GameSettings gameSettings) {
        this(Player.of(gameSettings.playerSettings().get(0)), Player.of(gameSettings.playerSettings().get(1)));
    }

    /**
     * Passes cell index to currentPlayer's makeShot() method.
     *
     * @param position cell index that is shot.
     * @return proper FieldState as a result of made shot.
     */
    public Result makeShot(int position) {
        Result result = singleShot.makeShot(position);
        return result;
    }

    String obtainUUID() {
        return id;
    }
}
