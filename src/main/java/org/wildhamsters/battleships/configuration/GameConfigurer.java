package org.wildhamsters.battleships.configuration;

import org.wildhamsters.battleships.fleet.ShipsPositions;

import java.util.List;
import java.util.Random;

/**
 * @author Dominik Żebracki
 */
public class GameConfigurer {

    private final ShipPlacementConfigurer shipPlacementConfigurer;

    public GameConfigurer(List<Integer> shipSizesToBePlaced,
                          int boardSize,
                          int height,
                          int width) {
        this.shipPlacementConfigurer = new ShipPlacementConfigurer(
                shipSizesToBePlaced, new ConfigurationBoard(height, width),
                boardSize, width, new Random());
    }

    public ShipsPositions createConfiguration() {
        return shipPlacementConfigurer.placeShips();
    }
}
