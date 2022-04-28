package org.wildhamsters.battleships;

/**
 * Contains both players. Indicate which is current (making shot) and which is enemy (fire upon).
 * Players are changed if current player missed.
 *
 * @author Piotr Chowaniec
 */
class SingleShot {

    private Player current;
    private Player enemy;

    public SingleShot(Player current, Player enemy) {
        this.current = current;
        this.enemy = enemy;
    }

    /**
     * Takes board cell index and checks what FieldState will be after shot.
     * It may be ACCURATE_SHOT if current player hit enemy's ship, MISSED_OUT.
     *
     * @param position index of cell that is being shot.
     * @return FieldState of cell after shot.
     */
    FieldState makeShot(int position) {
        FieldState state = enemy.enemyShotResult(position);
        if (state == FieldState.MISSED_SHOT) {
            switchPlayers();
        }
        return enemy.enemyShotResult(position);
    }

    private void switchPlayers() {
        Player temp = current;
        current = enemy;
        enemy = temp;
    }

    /**
     * Checks whether current player won the game after last shot.
     *
     * @return true is all enemy's ships are sunk, false otherwise.
     */
    boolean isWinner() {
        return enemy.isLost();
    }
}
