package org.wildhamsters.battleships.play;

import org.wildhamsters.battleships.board.Board;
import org.wildhamsters.battleships.board.FieldState;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Computes fields around the ship that is sinking.
 *
 * @author Piotr Chowaniec
 */
class SunkShipMarker {

    Set<Integer> fieldsToMark(List<Integer> shipPosition, Board board) {
        Set<Integer> fieldSet = new HashSet<>();
        for (int position : shipPosition) {
            for (int i : findFieldsAround(position, board.size().max())) {
                if (isFieldToBeMarkedAsMissedShot(position, i, board)) {
                    fieldSet.add(i);
                }
            }
        }
        return fieldSet;
    }

    private Set<Integer> findFieldsAround(int field, int size) {
        int side = (int) Math.sqrt(size + 1);
        return new HashSet<>(Set.of(
                field - side - 1, field - side, field - side + 1,
                field - 1, field + 1,
                field + side - 1, field + side, field + side + 1));
    }

    private boolean isFieldToBeMarkedAsMissedShot(int position, int field, Board board) {
        return board.size().isWithinDimension(field)
                && !isOnEdge(position, field, board.size().max() + 1)
                && board.getField(field) == FieldState.WATER;
    }

    private boolean isOnEdge(int position, int field, int size) {
        return isOnLeftEdge(position, field, size)
                || isOnRightEdge(position, field, size);
    }

    private boolean isOnLeftEdge(int position, int field, int size) {
        int side = (int) Math.sqrt(size);
        return position % side == 0 && field % side == side - 1;
    }

    private boolean isOnRightEdge(int position, int field, int size) {
        int side = (int) Math.sqrt(size);
        return position % side == side - 1 && field % side == 0;
    }
}
