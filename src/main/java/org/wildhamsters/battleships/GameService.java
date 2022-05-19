package org.wildhamsters.battleships;

import org.springframework.stereotype.Service;
import org.wildhamsters.battleships.configuration.GameConfigurer;
import org.wildhamsters.battleships.fleet.PositionsDTO;
import org.wildhamsters.battleships.play.GameRoom;
import org.wildhamsters.battleships.play.GameRooms;
import org.wildhamsters.battleships.play.MatchStatisticsEntityMapper;
import org.wildhamsters.battleships.play.MatchStatisticsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Main entry point to the game.
 * Manages connection of players and handles interactions between players and a game.
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
    private final MatchStatisticsRepository matchStatisticsRepository;
    private final AtomicBoolean isResponseWithShipsReady;
    private ConnectionStatus connectionStatus;

    GameService(GameConfigurer gameConfigurer, MatchStatisticsRepository matchStatisticsRepository) {
        this.gameConfigurer = gameConfigurer;
        this.gameRoom = null;
        this.isResponseWithShipsReady = new AtomicBoolean(false);
        this.connectedPlayers = new ConnectedPlayers(new ArrayList<>());
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
        if(!connectedPlayers.areBothConnected()) {
            return createPlayerWaitingForOpponentStatus();
        } else {
            return createTwoPlayersConnectedStatus();
        }
    }

    boolean areTwoPlayersConnected() {
        return connectedPlayers.areBothConnected();
    }

    /**
     *
     * @param position of a shot on the board.
     * @return result of the shot.
     */
    Result shoot(String roomId, int position) {
        Result result = gameRooms.findRoom(roomId).makeShot(position);
        if (result.finished()) {
            matchStatisticsRepository.save(
                    new MatchStatisticsEntityMapper().map(gameRooms.findRoom(roomId).getMatchStatistics()));
        }
        return result;
    }

    ConnectionStatus createPlayerWaitingForOpponentStatus() {
        return new ConnectionStatus("No opponents for now",
                null,
                connectedPlayers.firstOneConnected().sessionId(), null,
                null, null,
                null, null,
                null, Event.CONNECT);
    }

    void addPlayer(ConnectedPlayer connectedPlayer) {
        connectedPlayers = connectedPlayers.add(connectedPlayer);
    }

    private ConnectionStatus createTwoPlayersConnectedStatus() {
        var gameSettings = gameConfigurer.createConfiguration(SHIP_SIZES_TO_BE_CREATED,
                BOARD_HEIGHT, BOARD_WIDTH, connectedPlayers.names(), connectedPlayers.ids());
        this.gameRoom = new GameRoom(gameSettings);
        var roomId = gameRooms.addRoom(gameRoom);

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
        clearConnectedPlayersAfterPairing();
        return connectionStatus;
    }

    void createTwoPlayersConnectedStatus(List<PositionsDTO> shipsPositions) {
        var gameSettings = gameConfigurer.createConfiguration(shipsPositions,
                SHIP_SIZES_TO_BE_CREATED,
                BOARD_HEIGHT, BOARD_WIDTH, connectedPlayers.names(), connectedPlayers.ids());
        this.gameRoom = new GameRoom(gameSettings);
        var roomId = gameRooms.addRoom(gameRoom);

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
        clearConnectedPlayersAfterPairing();
        this.connectionStatus = connectionStatus;
        this.isResponseWithShipsReady.set(true);
    }

    ConnectionStatus getConnectionStatus() {
        while (!isResponseWithShipsReady.get()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        isResponseWithShipsReady.set(false);
        return this.connectionStatus;
    }

    private void clearConnectedPlayersAfterPairing() {
        connectedPlayers = new ConnectedPlayers(new ArrayList<>());
    }

    SurrenderResult surrender(String roomId, String surrenderPlayerSessionId) {
        String surrenderMessage = "You gave up.";
        String winnerMessage = "The opponent gave up. You won!";
        try {
            var winnerSessionId = gameRooms.findRoom(roomId).findSurrenderPlayerOpponent(surrenderPlayerSessionId);
            return new SurrenderResult(Event.SURRENDER, surrenderPlayerSessionId, winnerSessionId,
                    surrenderMessage, winnerMessage);
        } catch (IllegalArgumentException e) {
            return new SurrenderResult(Event.SURRENDER, surrenderPlayerSessionId, null,
                    surrenderMessage, winnerMessage);
        }
    }
}
