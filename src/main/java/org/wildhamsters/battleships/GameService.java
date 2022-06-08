package org.wildhamsters.battleships;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.wildhamsters.battleships.configuration.GameConfigurer;
import org.wildhamsters.battleships.play.*;

import java.util.ArrayList;
import java.util.List;

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
    private final MatchStatisticsRepository matchStatisticsRepository;
    private GameRoom gameRoom;
    private ConnectedPlayers connectedPlayers;

    GameService(MatchStatisticsRepository matchStatisticsRepository) {
        this.gameRoom = null;
        this.connectedPlayers = new ConnectedPlayers(new ArrayList<>());
        this.gameConfigurer = new GameConfigurer("https://protected-stream-19238.herokuapp.com/placeShips");
        this.matchStatisticsRepository = matchStatisticsRepository;
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

    List<SingleMatchStatistics> findAllStatistics() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:5500/";
        return new ArrayList<>(restTemplate.getForObject(url, StatisticsDTO.class).singleMatchStatisticsList());
    }

    private void saveMatchStatistics(String roomId) {
        matchStatisticsRepository.save(
                new MatchStatisticsEntityMapper().map(gameRooms.findRoom(roomId).getMatchStatistics()));
    }
}
