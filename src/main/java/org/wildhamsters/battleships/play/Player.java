package org.wildhamsters.battleships.play;

import org.wildhamsters.battleships.board.Board;
import org.wildhamsters.battleships.board.FieldState;
import org.wildhamsters.battleships.configuration.GameSettings;
import org.wildhamsters.battleships.fleet.Fleet;

/**
 * @author Piotr Chowaniec
 */
class Player {

    private final String id;
    private final String name;
    private final Board board;
    private final Fleet fleet;
    private final ShotVerifier shotVerifier;

    Player(String id, String name, Board board, Fleet fleet, ShotVerifier shotVerifier) {
        this.id = id;
        this.name = name;
        this.board = board;
        this.fleet = fleet;
        this.shotVerifier = shotVerifier;
    }

    // Factory method for tests
    static Player of(String id, String name, Board board, Fleet fleet) {
        return new Player(id, name, board, fleet, new ShotVerifier());
    }

    static Player of(GameSettings.PlayerSettings playerSettings) {
        return new Player(playerSettings.id(),
                playerSettings.name(),
                playerSettings.board(),
                playerSettings.fleet(),
                new ShotVerifier());
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

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }
}
