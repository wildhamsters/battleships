package org.wildhamsters.battleships;

import java.util.*;
import java.util.stream.IntStream;

/**
 * @author Dominik Żebracki
 */
class ConfigurationBoard {

    private final Map<Integer, FieldState> board;
    private final int height;
    private final int width;

    public ConfigurationBoard(int gameBoardHeight, int gameBoardWidth) {
        this.height = gameBoardHeight + 2;
        this.width = gameBoardWidth + 2;
        this.board = fillBoard();
    }

    private Map<Integer, FieldState> fillBoard() {
        var newBoard = new HashMap<Integer, FieldState>();
        IntStream.range(0, height*width-1).forEach(i -> newBoard.put(i, FieldState.WATER));
        IntStream.range(0, width).forEach(i -> newBoard.put(i, FieldState.WALL));
        IntStream.range(1, height)
                .forEach(i -> {newBoard.put(rowBegin(i), FieldState.WALL); newBoard.put(rowEnd(i), FieldState.WALL);});
        IntStream.range(rowBegin(height - 1), rowEnd(height - 1)).forEach(i -> newBoard.put(i, FieldState.WALL));
        return newBoard;
    }

    boolean isEnoughSpaceForShipWithinBoardBounds(int requiredSpace, int gameBoardPosition) {
        var position = toConfigurationBoardPosition(gameBoardPosition);
        return rowEnd(rowIndex(position)) - position >= requiredSpace;
    }

    boolean areSurroundingFieldsNotOccupied(List<Integer> gameBoardPositions) {
        return gameBoardPositions.stream()
                .map(this::toConfigurationBoardPosition)
                .map(this::fieldSurrounding)
                .flatMap(Collection::stream)
                .map(board::get)
                .noneMatch(fieldState -> fieldState == FieldState.SHIP);
    }

    private int toConfigurationBoardPosition(int gameBoardPosition) {
        var gameBoardRowIndex = gameBoardPosition / (width - 2);
        return gameBoardPosition + width + (gameBoardRowIndex * 2) + 1;
    }

    boolean canShipBePlaced(List<Integer> shipMastPositions) {
        return isEnoughSpaceForShipWithinBoardBounds(shipMastPositions.size(), shipMastPositions.get(0)) &&
                areSurroundingFieldsNotOccupied(shipMastPositions);
    }

    void placeShip(List<Integer> shipFields) {
        shipFields.stream()
                .map(this::toConfigurationBoardPosition)
                .forEach(i -> board.put(i, FieldState.SHIP));
    }

    List<Integer> fieldSurrounding(int position) {
        var surrounding = new ArrayList<Integer>();
        surrounding.add(northWest(position));
        surrounding.add(north(position));
        surrounding.add(northEast(position));
        surrounding.add(east(position));
        surrounding.add(southEast(position));
        surrounding.add(south(position));
        surrounding.add(southWest(position));
        surrounding.add(west(position));
        return surrounding;
    }

    private int rowIndex(int position) {
        return position / width;
    }

    private int rowBegin(int rowIndex) {
        return rowIndex * width;
    }

    private int rowEnd(int rowIndex) {
        return rowBegin(rowIndex) + width - 1;
    }


    private int northWest(int position) {
        return position - width - 1;
    }

    private int north(int position) {
        return position - width;
    }

    private int northEast(int position) {
        return position - width + 1;
    }

    private int east(int position) {
        return position + 1;
    }

    private int southEast(int position) {
        return position + width + 1;
    }

    private int south(int position) {
        return position + width;
    }

    private int southWest(int position) {
        return position + width - 1;
    }

    private int west(int position) {
        return position - 1;
    }

    private enum FieldState {
        WATER("."), WALL("*"), SHIP("#");
        private final String representation;

        FieldState(String representation) {
            this.representation = representation;
        }

        @Override
        public String toString() {
            return representation;
        }
    }

    void printBoard() {
        String result = "";
        for (int i = 1; i <= width*height; i++) {
            result += board.get(i - 1).toString() + " ";
            if(i % width == 0) {
                result += "\n";
            }
        }
        System.out.println(result);
    }
}
