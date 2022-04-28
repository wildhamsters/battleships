package org.wildhamsters.battleships;

import org.springframework.stereotype.Service;
import org.wildhamsters.battleships.board.Board;
import org.wildhamsters.battleships.board.FieldState;
import org.wildhamsters.battleships.fleet.Fleet;
import org.wildhamsters.battleships.play.IllegalShotException;

/**
 * @author Piotr Chowaniec
 */
@Service
public
class GameService {

    private final Board board;
    private final Fleet fleet;

    public GameService(Board board) {
        this.board = board;
        fleet = new Fleet();
    }

    public FieldState verifyShot(int position) throws IllegalShotException {
        fleet.makeShot(position);
        return null;
    }

    void resetGameStatus() {
        board.clearBoard();
    }

    public boolean isRoundFinished() {
        return fleet.checkIfAllShipsSunk();
    }

    private void updateFieldState(FieldState newState, int position) {
        board.setField(newState, position);
    }
}
