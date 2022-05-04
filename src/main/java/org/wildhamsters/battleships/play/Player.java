package org.wildhamsters.battleships.play;

import org.wildhamsters.battleships.board.Board;
import org.wildhamsters.battleships.board.FieldState;
import org.wildhamsters.battleships.configuration.GameSettings;
import org.wildhamsters.battleships.fleet.Fleet;
import org.wildhamsters.battleships.fleet.ShotResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Contains all data of Player such as his id, name, board, fleet.
 * ShotVerifier to validate shots.
 * Its methods interact with fleet and board to update it after shot and indicate when player lost the match.
 *
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
     * Takes index of the board cell, checks if it is in range and gets the FieldState that will be set
     * after shot. If the shot is not the range of board then IllegalShotException is being thrown.
     * <p>
     * Shot cell is updated in the board.
     * Position is passed to fleet which updates its internals.
     *
     * @param position index of cell that is being shot.
     * @return FieldState of cell being shot.
     * @throws IllegalShotException when shot is out of board.
     */
    FieldState enemyShotResult(int position) throws IllegalShotException {
        return shotVerifier.verifyShot(position, board);
    }

    /**
     * Cell is being updated in the board.
     * Position is passed to fleet which updates its internals.
     * If ship is sinking then Map of fields to be set as missed shots around the ship is returned.
     * Returned map always contains updated state of shot cell.
     *
     * @param position index of cell that is being shot.
     * @param state    FieldState of cell that will be set after shot.
     * @return Map of cells to be updated after shot.
     */
    Map<Integer, FieldState> takeShot(int position, FieldState state) {
        var fields = new HashMap<Integer, FieldState>();
        updateFieldState(state, position);
        fields.put(position, state);

        ShotResult shotResult = fleet.makeShot(position);
        if (shotResult == ShotResult.SHIP_SUNK) {
            getFieldsAroundSinkingShip(position).forEach(i -> {
                fields.put(i, FieldState.MISSED_SHOT);
                board.setField(FieldState.MISSED_SHOT, i);
            });
        }
        return fields;
    }

    private Set<Integer> getFieldsAroundSinkingShip(int position) {
        return new SunkShipMarker().fieldsToMark(fleet.getSinkingShipPosition(position), board);
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
