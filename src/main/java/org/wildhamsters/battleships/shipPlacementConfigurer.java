package org.wildhamsters.battleships;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author Dominik Żebracki
 */
class shipPlacementConfigurer {

    private final List<Integer> shipSizesToBePlaced;
    private final ConfigurationBoard board;
    private final int boardSize;
    private final int width;

    shipPlacementConfigurer(List<Integer> shipsToBePlaced, ConfigurationBoard board, int boardSize, int width) {
        this.shipSizesToBePlaced = shipsToBePlaced;
        this.board = board;
        this.boardSize = boardSize;
        this.width = width;
    }

    void placeShips() {
        var random = new Random();
        List<Integer> mastPositions;
        boolean direction;
        for (Integer size: shipSizesToBePlaced) {

            board.placeShip(createRandomValidMastPositions(size));
        }
    }

    private List<Integer> calculateMastPositions(int shipSize, int startingPosition, boolean horizontal) {
        var mastPositions = new ArrayList<Integer>();
        if(horizontal) {
            IntStream.range(0, shipSize).forEach(i -> mastPositions.add(startingPosition + i));
        } else {
            IntStream.range(0, shipSize).forEach(i -> mastPositions.add(startingPosition + i * width));
        }
        return mastPositions;
    }

    private List<Integer> createRandomValidMastPositions(int shipSize) {
        var random = new Random();
        boolean direction;
        List<Integer> mastPositions;
        do {
            direction = random.nextInt(0, 2) == 0;
            mastPositions = calculateMastPositions(shipSize, random.nextInt(boardSize), direction);
        } while (!board.canShipBePlaced(mastPositions, direction));
        return mastPositions;
    }
}
