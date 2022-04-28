package org.wildhamsters.battleships;

import org.springframework.stereotype.Service;

/**
 * @author Piotr Chowaniec
 */
@Service
class GameService {

    private final ShotVerifier shotVerifier;
    private final Board board;
    private final Fleet fleet;

    GameService(Board board) {
        this.board = board;
        shotVerifier = new ShotVerifier(board.size());
        fleet = new Fleet();
        placeShipsOnBoard();
    }

    FieldState verifyShot(int position) throws IllegalShotException {
        FieldState state = shotVerifier.verifyShot(position, board);
        fleet.makeShot(position);
        updateFieldState(state, position);
        return state;
    }

    void resetGameStatus() {
        board.clearBoard();
        fleet.resetAllShipsToUntouched();
        placeShipsOnBoard();
    }

    boolean isRoundFinished() {
        return fleet.checkIfAllShipsSunk();
    }

    private void placeShipsOnBoard() {
        for(Integer position : fleet.allTakenFields.getAllFieldsInOneList()) {
            board.setField(FieldState.INTACT_SHIP, position);
        }
    }

    private void updateFieldState(FieldState newState, int position) {
        board.setField(newState, position);
    }
}
