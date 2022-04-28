package org.wildhamsters.battleships;

/**
 * Container for both players. Gives access to making shot and checking whether round is finished.
 *
 * @author Piotr Chowaniec
 */
class GameRoom {

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
    FieldState makeShot(int position) {
        return singleShot.makeShot(position);
    }

    /**
     * Checks whether a player won the game after last shot.
     *
     * @return true if a player's fleet is sunk, false otherwise.
     */
    boolean isRoundFinished() {
        return singleShot.isWinner();
    }
}
