package org.wildhamsters.battleships;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Dominik Żebracki
 */
class ShipPlacementConfigurer {

    private final List<Integer> shipSizesToBePlaced;
    private final ConfigurationBoard board;
    private final int boardSize;

    ShipPlacementConfigurer(List<Integer> shipsToBePlaced, ConfigurationBoard board, int boardSize) {
        this.shipSizesToBePlaced = shipsToBePlaced;
        this.board = board;
        this.boardSize = boardSize;
    }

    void placeShips() {
        var random = new Random();
        Integer startingPosition;
        var shipMastPositions = new ArrayList<Integer>();
        for (Integer size: shipSizesToBePlaced) {
            do {
                startingPosition = random.nextInt(boardSize);
                shipMastPositions = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    shipMastPositions.add(startingPosition + i);
                }
                System.out.println("MastPos: " + shipMastPositions);

            } while (!board.canShipBePlaced(shipMastPositions));
            board.placeShip(shipMastPositions);
        }
    }
}
