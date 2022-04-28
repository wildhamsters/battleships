package org.wildhamsters.battleships.configuration;

import org.wildhamsters.battleships.fleet.ShipsPositions;

import java.util.List;
import java.util.Random;

/**
 * @author Dominik Żebracki
 */
public class GameConfigurer {

    public ShipsPositions createConfiguration(List<Integer> shipSizesToBePlaced,
                                              int height,
                                              int width) {
        var shipPlacementConfigurer = new ShipPlacementConfigurer(height, width, new Random());
        return shipPlacementConfigurer.placeShips(shipSizesToBePlaced);
    }
}
