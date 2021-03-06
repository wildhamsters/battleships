package org.wildhamsters.battleships;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.wildhamsters.battleships.configuration.GameConfigurer;
import org.wildhamsters.battleships.play.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

//TODO refactor this class

/**
 * Main entry point to the game.
 * Manages connection of players and handles interactions between players and a
 * game.
 *
 * @author Dominik Żebracki
 */
@Service
class GameService {

    private static final List<Integer> SHIP_SIZES_TO_BE_CREATED = List.of(4, 3, 3, 2, 2, 2, 1, 1, 1, 1);
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 10;

    private final GameRooms gameRooms = new GameRooms();
    private final GameConfigurer gameConfigurer;
    private GameRoom gameRoom;
    private ConnectedPlayers connectedPlayers;

    GameService() {
        this.gameRoom = null;
        this.connectedPlayers = new ConnectedPlayers(new ArrayList<>());
        this.gameConfigurer = new GameConfigurer("https://protected-stream-19238.herokuapp.com/placeShips");
    }

    /**
     * Manages process of connecting players.
     * After connecting the second player, ships and boards are created.
     *
     * @param connectedPlayer details of connected players.
     * @return players fleets, boards and their identifiers.
     */
    ConnectionStatus processConnectingPlayers(ConnectedPlayer connectedPlayer) {
        connectedPlayers = connectedPlayers.add(connectedPlayer);
        if (!connectedPlayers.areBothConnected()) {
            return createPlayerWaitingForOpponentStatus();
        } else {
            return createTwoPlayersConnectedStatus();
        }
    }

    /**
     * @param position of a shot on the board.
     * @return result of the shot.
     */
    Result shoot(String roomId, int position) {
        Result result = gameRooms.findRoom(roomId).makeShot(position);
        if (result.finished()) {
            saveMatchStatistics(roomId);
        }
        return result;
    }

    private ConnectionStatus createPlayerWaitingForOpponentStatus() {
        return new ConnectionStatus("No opponents for now",
                null,
                connectedPlayers.firstOneConnected().sessionId(), null,
                null, null,
                null, null,
                null, Event.CONNECT);
    }

    private ConnectionStatus createTwoPlayersConnectedStatus() {
        var gameSettings = gameConfigurer.createConfiguration(SHIP_SIZES_TO_BE_CREATED,
                BOARD_HEIGHT, BOARD_WIDTH, connectedPlayers.names(), connectedPlayers.ids());
        this.gameRoom = new GameRoom(gameSettings);
        var roomId = gameRooms.addRoom(gameRoom);
        // TODO refactor Optionals
        var connectionStatus = new ConnectionStatus("Players paired.",
                roomId,
                connectedPlayers.firstOneConnected().sessionId(),
                gameSettings.firstPlayersFleet().getFleetPositions(),
                connectedPlayers.secondOneConnected().sessionId(),
                gameSettings.secondPlayersFleet().getFleetPositions(),
                connectedPlayers.firstOneConnected().name(),
                connectedPlayers.firstOneConnected().name(),
                connectedPlayers.secondOneConnected().name(),
                Event.CONNECT);

        Logger.log(Log.Level.INFO, this.getClass(), "Players  %s | %s  started new game in room %s.".formatted(
                connectedPlayers.firstOneConnected().name(), connectedPlayers.secondOneConnected().name(),
                roomId));

        clearConnectedPlayersAfterPairing();
        return connectionStatus;
    }

    private void clearConnectedPlayersAfterPairing() {
        connectedPlayers = new ConnectedPlayers(new ArrayList<>());
    }

    SurrenderResult surrender(String roomId, String surrenderPlayerSessionId) {
        String surrenderMessage = "You gave up.";
        String winnerMessage = "The opponent gave up. You won!";
        try {
            var winnerSessionId = gameRooms.findRoom(roomId).findSurrenderPlayerOpponent(surrenderPlayerSessionId);
            saveMatchStatistics(roomId);
            return new SurrenderResult(Event.SURRENDER, surrenderPlayerSessionId, winnerSessionId,
                    surrenderMessage, winnerMessage);
        } catch (IllegalArgumentException e) {
            return new SurrenderResult(Event.SURRENDER, surrenderPlayerSessionId, null,
                    surrenderMessage, winnerMessage);
        }
    }

    @SuppressFBWarnings(
            value = "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE",
            justification = "SpotBugs indicates NPE which is handled with Optional"
    )
    List<SingleMatchStatistics> findAllStatistics() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:5500/";
        Optional<List<SingleMatchStatistics>> statistics =
                Optional.of(restTemplate.getForObject(url, StatisticsDTO.class).singleMatchStatisticsList());
        return statistics.orElseGet(ArrayList::new);
    }

    private void saveMatchStatistics(String roomId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:5500/";
        CurrentMatchStatistics current = new CurrentMatchStatisticsMapper().map(gameRooms.findRoom(roomId).getMatchStatistics());
        restTemplate.postForObject(url, current, CurrentMatchStatistics.class);
    }
}
