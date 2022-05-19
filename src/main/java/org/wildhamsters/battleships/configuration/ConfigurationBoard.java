package org.wildhamsters.battleships.configuration;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Special board used only for placing fleet purposes.
 * Contains additional two columns and rows that constitute "frame" around a regular game board.
 * Indexes of a regular board are translated accordingly.
 *
 * @author Dominik Żebracki
 */
class ConfigurationBoard {

    private final Map<Integer, FieldState> board;
    private final BoardStructure structure;

    ConfigurationBoard(int gameBoardHeight, int gameBoardWidth) {
        this.structure = new BoardStructure(gameBoardWidth + 2, gameBoardHeight + 2);
        this.board = fillBoard();
    }

    /**
     * Places ship on a board. No other subsequently added ship can interfere with already placed ships.
     *
     * @param shipFields positions of a ship to place.
     */
    void placeShip(List<Integer> shipFields) {
        shipFields.stream()
                .map(this::toConfigurationBoardPosition)
                .forEach(i -> board.put(i, FieldState.SHIP));
    }

    /**
     * Checks if a ship can be placed by searching board if space is not occupied.
     *
     * @param shipMastPositions number of ships masts
     * @param direction direction of a ship
     * @return If ship can be placed.
     */
    boolean canShipBePlaced(List<Integer> shipMastPositions, ShipDirection direction) {
        return isEnoughSpaceForShipWithinBoardBounds(shipMastPositions.size(), shipMastPositions.get(0), direction) &&
                areSurroundingFieldsNotOccupied(shipMastPositions);
    }

    private boolean isEnoughSpaceForShipWithinBoardBounds(int requiredSpace, int gameBoardPosition, ShipDirection direction) {
        var position = toConfigurationBoardPosition(gameBoardPosition);
        return switch (direction) {
            case HORIZONTAL -> structure.rowEnd(structure.rowIndex(position)) - position >= requiredSpace;
            case VERTICAL -> structure.height() - structure.rowIndex(position) > requiredSpace;
        };
    }

    private boolean areSurroundingFieldsNotOccupied(List<Integer> gameBoardPositions) {
        return gameBoardPositions.stream()
                .map(this::toConfigurationBoardPosition)
                .map(structure::fieldSurrounding)
                .flatMap(Collection::stream)
                .map(board::get)
                .noneMatch(fieldState -> fieldState == FieldState.SHIP);
    }

    private int toConfigurationBoardPosition(int gameBoardPosition) {
        var gameBoardRowIndex = gameBoardPosition / (structure.width() - 2);
        return gameBoardPosition + structure.width() + (gameBoardRowIndex * 2) + 1;
    }

    private Map<Integer, FieldState> fillBoard() {
        var newBoard = new HashMap<Integer, FieldState>();
        IntStream.range(0, structure.size()-1).forEach(i -> newBoard.put(i, FieldState.WATER));
        IntStream.range(0, structure.width()).forEach(i -> newBoard.put(i, FieldState.WALL));
        IntStream.range(1, structure.height())
                .forEach(i -> {newBoard.put(structure.rowBegin(i), FieldState.WALL);
                    newBoard.put(structure.rowEnd(i), FieldState.WALL);});
        IntStream.range(structure.rowBegin(structure.height() - 1), structure.rowEnd(structure.height() - 1))
                .forEach(i -> newBoard.put(i, FieldState.WALL));
        return newBoard;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        IntStream.range(1, structure.size()+1)
                .forEach(i -> {
                    sb.append(board.get(i - 1).toString()).append(" ");
                    if(i % structure.width() == 0) {
                        sb.append(System.lineSeparator());
                    }
                });
        return sb.toString();
    }

    enum FieldState {
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
}
