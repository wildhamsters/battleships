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
    private final Random random;

    ShipPlacementConfigurer(List<Integer> shipSizedToBePlaced,
                            ConfigurationBoard board,
                            int boardSize,
                            int width,
                            Random random) {
        shipSizedToBePlaced.sort(Comparator.reverseOrder());
        this.shipSizesToBePlaced = shipSizedToBePlaced;
        this.board = board;
        this.boardSize = boardSize;
        this.width = width;
        this.random = random;
    }

    //TODO relaunch placing randomizer.
    // Sometimes enters infinite loop with unlucky placing with small board and a lot of ships
    // works fine with standard settings 10x10, standard fleet
    ShipsPosition placeShips() {
        var fleet = new ArrayList<List<Integer>>();
        List<Integer> shipPositions;
        for (Integer shipSize : shipSizesToBePlaced) {
            shipPositions = createRandomValidMastPositions(shipSize);
            board.placeShip(shipPositions);
            fleet.add(shipPositions);
        }
        return new ShipsPosition(fleet);
    }

    private List<Integer> calculateMastPositions(int shipSize, int startingPosition, ShipDirection direction) {
        var mastPositions = new ArrayList<Integer>();
        switch (direction) {
            case HORIZONTAL -> IntStream.range(0, shipSize).forEach(i -> mastPositions.add(startingPosition + i));
            case VERTICAL -> IntStream.range(0, shipSize).forEach(i -> mastPositions.add(startingPosition + i * width));
            default -> throw new IllegalArgumentException("Unknown ship placement direction.");
        }
        return mastPositions;
    }

    private List<Integer> createRandomValidMastPositions(int shipSize) {
        var direction = establishShipDirection();
        List<Integer> mastPositions;
        do {
            mastPositions = calculateMastPositions(shipSize, random.nextInt(boardSize), direction);
        } while (!board.canShipBePlaced(mastPositions, direction));
        return mastPositions;
    }

    private ShipDirection establishShipDirection() {
        return switch (random.nextInt(ShipDirection.values().length)) {
            case 0 -> ShipDirection.HORIZONTAL;
            case 1 -> ShipDirection.VERTICAL;
            default -> ShipDirection.VERTICAL;
        };
    }
}
