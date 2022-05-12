package org.wildhamsters.battleships.configuration;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.web.client.RestTemplate;
import org.wildhamsters.battleships.board.Board;
import org.wildhamsters.battleships.fleet.Fleet;
import org.wildhamsters.battleships.fleet.ShipsPositions;

/**
 * Creates configuration of a whole game.
 *
 * @author Dominik Żebracki
 */
public class GameConfigurer {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "https://protected-stream-19238.herokuapp.com/placeShips";

        ShipsPositions sp = restTemplate.getForObject(resourceUrl, ShipsPositions.class);

        public GameSettings createConfiguration(List<Integer> shipSizesToBePlaced,
                        int boardHeight,
                        int boardWidth,
                        List<String> playersNames,
                        List<String> playersIds) {

                return new GameSettings(IntStream.range(0, playersNames.size())
                                .boxed()
                                .map(i -> createPlayerSettings(
                                                restTemplate.getForObject(resourceUrl, ShipsPositions.class),
                                                playersIds.get(i),
                                                playersNames.get(i), shipSizesToBePlaced))
                                .toList());
        }

        private GameSettings.PlayerSettings createPlayerSettings(ShipsPositions shipPositions,
                        String id,
                        String playerName,
                        List<Integer> shipSizesToBePlaced) {
                return new GameSettings.PlayerSettings(id,
                                playerName,
                                Board.create(shipPositions),
                                new Fleet(shipPositions));
        }
}
