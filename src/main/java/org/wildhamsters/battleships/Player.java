package org.wildhamsters.battleships;

/**
 * @author Piotr Chowaniec
 */
class Player {

    private final int id;
    private final Board board;
    private final Fleet fleet;
    private final ShotVerifier shotVerifier;

    Player(int id, Board board, Fleet fleet, ShotVerifier shotVerifier) {
        this.id = id;
        this.board = board;
        this.fleet = fleet;
        this.shotVerifier = shotVerifier;
    }

    static Player of(int id, Board board) {
        return new Player(id, board, new Fleet(), new ShotVerifier(board.size()));
    }

    FieldState enemyShotResult(int position) throws IllegalShotException {
        FieldState state = shotVerifier.verifyShot(position, board);
        fleet.makeShot(position);
        updateFieldState(state, position);
        return state;
    }

    private void updateFieldState(FieldState newState, int position) {
        board.setField(newState, position);
    }

    boolean isLost() {
        return fleet.checkIfAllShipsSunk();
    }
}
