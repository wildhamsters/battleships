package org.wildhamsters.battleships.configuration;

import org.wildhamsters.battleships.board.Board;
import org.wildhamsters.battleships.fleet.Fleet;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Creates configuration of a whole game.
 *
 * @author Dominik Żebracki
 */
public class GameConfigurer {

    public GameSettings createConfiguration(List<Integer> shipSizesToBePlaced,
                                            int boardHeight,
                                            int boardWidth,
                                            List<String> playersNames,
                                            List<String> playersIds) {
        var shipPlacementConfigurer = new ShipPlacementConfigurer(boardHeight, boardWidth, new Random());
        return new GameSettings(IntStream.range(0, playersNames.size())
                .boxed()
                .map(i -> createPlayerSettings(shipPlacementConfigurer, playersIds.get(i),
                        playersNames.get(i), shipSizesToBePlaced))
                .toList());
    }

    private GameSettings.PlayerSettings createPlayerSettings(ShipPlacementConfigurer shipPlacementConfigurer,
                                                             String id,
                                                             String playerName,
                                                             List<Integer> shipSizesToBePlaced) {
        var shipPositions = shipPlacementConfigurer.placeShips(shipSizesToBePlaced);
        return new GameSettings.PlayerSettings(id,
                playerName,
                Board.create(shipPositions),
                new Fleet(shipPositions));
    }
}
