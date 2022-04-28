package org.wildhamsters.battleships;

/**
 * @author Mariusz Bal
 */
class Result {

    private final int cell;
    private final FieldState updatedState;
    private final Boolean finished;
    private final String error;
    private final String currentTurnPlayer;
    private final String currentTurnPlayerName;

    Result(int cell, FieldState updatedState, Boolean finished, String error, String currentTurnPlayer, String currentTurnPlayerName) {
        this.cell = cell;
        this.updatedState = updatedState;
        this.finished = finished;
        this.error = error;
        this.currentTurnPlayer = currentTurnPlayer;
        this.currentTurnPlayerName = currentTurnPlayerName;
    }

    // getters are here for Jackson to work properly while creating JSON

    public int getCell() {
        return cell;
    }

    public FieldState getUpdatedState() {
        return updatedState;
    }

    public Boolean getFinished() {
        return finished;
    }

    public String getError() {
        return error;
    }

    public String getCurrentTurnPlayer() {
        return currentTurnPlayer;
    }

    public String getCurrentTurnPlayerName() {
        return currentTurnPlayerName;
    }
}
