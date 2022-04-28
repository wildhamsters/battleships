package org.wildhamsters.battleships.board;

import org.wildhamsters.battleships.fleet.ShipPosition;
import org.wildhamsters.battleships.fleet.ShipsPositions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author Dominik Żebracki
 */
public class ShipPlacementConfigurer {

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

    // TODO relaunch placing randomizer.
    //  Sometimes enters infinite loop with unlucky placing with small board and a lot of ships
    //  works fine with standard settings 10x10, standard fleet
    public ShipsPositions placeShips() {
        var fleet = new ArrayList<List<Integer>>();
        shipSizesToBePlaced.stream()
                .map(this::createRandomValidMastPositions)
                .forEach(p -> {board.placeShip(p); fleet.add(p);});
        return new ShipsPositions(fleet.stream().map(ShipPosition::new).toList());
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
