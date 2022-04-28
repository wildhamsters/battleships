package org.wildhamsters.battleships.play;

import org.springframework.stereotype.Service;
import org.wildhamsters.battleships.board.Board;
import org.wildhamsters.battleships.board.FieldState;
import org.wildhamsters.battleships.fleet.Fleet;

/**
 * @author Piotr Chowaniec
 */
@Service
public
class GameService {

    private final ShotVerifier shotVerifier;
    private final Board board;
    private final Fleet fleet;

    public GameService(Board board) {
        this.board = board;
        shotVerifier = new ShotVerifier();
        fleet = new Fleet();
//        placeShipsOnBoard();
    }

    public FieldState verifyShot(int position) throws IllegalShotException {
        FieldState state = shotVerifier.verifyShot(position, board);
        fleet.makeShot(position);
        updateFieldState(state, position);
        return state;
    }

    void resetGameStatus() {
        board.clearBoard();
//        fleet.resetAllShipsToUntouched();
//        placeShipsOnBoard();
    }

    public boolean isRoundFinished() {
        return fleet.checkIfAllShipsSunk();
    }

//    private void placeShipsOnBoard() {
//        for(Integer position : fleet.allTakenFields.getAllFieldsInOneList()) {
//            board.setField(FieldState.INTACT_SHIP, position);
//        }
//    }

    private void updateFieldState(FieldState newState, int position) {
        board.setField(newState, position);
    }
}
