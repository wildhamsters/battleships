package org.wildhamsters.battleships;

class ShootVerificator {
    private final BoardDimension dimension;

    ShootVerificator(BoardDimension boardDimension) {
        this.dimension = boardDimension;
    }

    FieldState verificateShoot(int position, Board board) throws IllegalShootException {

        if (invalidShoot(position, board)) {
            throw new IllegalShootException();
        }

        return switch (board.getFiled(position)) {
            case WATER -> FieldState.MISSED_SHOT;
            case INTACT_SHIP -> FieldState.ACCURATE_SHOT;
            default -> throw new IllegalShootException();
        };
    }

    private boolean invalidShoot(int position, Board board) {
        return !dimension.isWithinDimension(position) || alreadyShot(position, board);
    }

    private boolean alreadyShot(int position, Board board) {
        return switch (board.getFiled(position)) {
            case WATER, INTACT_SHIP -> false;
            case ACCURATE_SHOT, MISSED_SHOT -> true;
        };
    }

}
