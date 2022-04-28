package org.wildhamsters.battleships;

import org.wildhamsters.battleships.fleet.Fleet;

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

    /**
     * Takes index of the board cell and checks if it is in range, and gets the FieldState that will be set
     * after shot. If the shot is not the range of board then IllegalShotException is being thrown.
     * Shot cell is updated in the board.
     * Position is passed to fleet which updates its internals.
     *
     * @param position index of cell that is being shot.
     * @return FieldState of cell after shot.
     * @throws IllegalShotException when shot is out of board.
     */
    FieldState enemyShotResult(int position) throws IllegalShotException {
        FieldState state = shotVerifier.verifyShot(position, board);
        updateFieldState(state, position);
        fleet.makeShot(position);
        return state;
    }

    private void updateFieldState(FieldState newState, int position) {
        board.setField(newState, position);
    }

    /**
     * Checks if the player lost the game.
     *
     * @return true if whole fleet is sunk, false otherwise.
     */
    boolean isLost() {
        return fleet.checkIfAllShipsSunk();
    }
}
