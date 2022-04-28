package org.wildhamsters.battleships.play;

import org.wildhamsters.battleships.Result;

/**
 * Container for both players. Gives access to making shot and checking whether round is finished.
 *
 * @author Piotr Chowaniec
 */
public class GameRoom {

    private Player playerOne;
    private Player playerTwo;
    private SingleShot singleShot;

    GameRoom(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        singleShot = new SingleShot(playerOne, playerTwo);
    }

    /**
     * Passes cell index to currentPlayer's makeShot() method.
     *
     * @param position cell index that is shot.
     * @return proper FieldState as a result of made shot.
     */
    Result makeShot(int position) {
        return singleShot.makeShot(position);
    }
}
