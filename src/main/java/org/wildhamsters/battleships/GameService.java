package org.wildhamsters.battleships;

import org.springframework.stereotype.Service;

/**
 * @author Piotr Chowaniec
 */
@Service
class GameService {

    private final ShootVerifier shootVerifier;
    private final Board board;
    private final Fleet fleet;

    GameService(Board board) {
        this.board = board;
        shootVerifier = new ShootVerifier(board.size());
        fleet = new Fleet();        // Call new Fleet(List<List<Integer>>
        placeShipsOnBoard();
    }

    FieldState verifyShoot(int position) throws IllegalShootException {
        FieldState state = shootVerifier.verifyShoot(position, board);
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
