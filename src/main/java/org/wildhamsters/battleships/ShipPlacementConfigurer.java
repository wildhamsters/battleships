package org.wildhamsters.battleships;

import java.util.*;
import java.util.stream.IntStream;

/**
 * @author Dominik Żebracki
 */
class ShipPlacementConfigurer {

    private final List<Integer> shipSizesToBePlaced;
    private final int boardSize;
    private final int width;
    private final ConfigurationBoard board;

    ShipPlacementConfigurer(List<Integer> shipSizedToBePlaced, ConfigurationBoard board, int boardSize, int width) {
        shipSizedToBePlaced.sort(Comparator.reverseOrder());
        this.shipSizesToBePlaced = shipSizedToBePlaced;
        this.board = board;
        this.boardSize = boardSize;
        this.width = width;
    }

    //TODO relaunch placing randomizer.
    // Sometimes enters infinite loop with unlucky placing with small board and a lot of ships
    // works fine with standard settings 10x10, standard fleet
    List<List<Integer>> placeShips() {
        var fleet = new ArrayList<List<Integer>>();
        List<Integer> shipPositions = null;
        for (Integer shipSize : shipSizesToBePlaced) {
            shipPositions = createRandomValidMastPositions(shipSize);
            board.placeShip(shipPositions);
            fleet.add(shipPositions);
        }
        return fleet;
    }

    private List<Integer> calculateMastPositions(int shipSize, int startingPosition, boolean horizontal) {
        var mastPositions = new ArrayList<Integer>();
        if (horizontal) {
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
