package org.wildhamsters.battleships;

/**
 * @author Mariusz Bal
 */
class Result {

    private final int cell;
    private final FieldState updatedState;
    private final Boolean finished;
    private final String error;

    Result(int cell, FieldState updatedState, Boolean finished, String error) {
        this.cell = cell;
        this.updatedState = updatedState;
        this.finished = finished;
        this.error = error;
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
}
