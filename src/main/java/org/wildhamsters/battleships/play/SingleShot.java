package org.wildhamsters.battleships.play;

import org.wildhamsters.battleships.Event;
import org.wildhamsters.battleships.Result;
import org.wildhamsters.battleships.board.FieldState;

/**
 * Contains both players. Indicate which is current (making shot) and which is enemy (fire upon).
 * Players are changed if current player missed.
 *
 * @author Piotr Chowaniec
 */
class SingleShot {

    private Player current;
    private Player enemy;

    SingleShot(Player current, Player enemy) {
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
    Result makeShot(int position) {
        String error = "";
        FieldState state = null;
        try {
            state = enemy.enemyShotResult(position);
        } catch (IllegalShotException e) {
            error = e.getMessage();
        }
        switchPlayers(state);

        return new Result(Event.GAMEPLAY, position, state, isWinner(), error, current.getId(), current.getName(), enemy.getId());
    }

    private void switchPlayers(FieldState state) {
        if (state == FieldState.MISSED_SHOT) {
            Player temp = current;
            current = enemy;
            enemy = temp;
        }
    }

    private boolean isWinner() {
        return enemy.isLost();
    }
}
