package org.wildhamsters.battleships;

import org.springframework.stereotype.Service;
import org.wildhamsters.battleships.configuration.GameConfigurer;
import org.wildhamsters.battleships.play.GameRoom;
import org.wildhamsters.battleships.play.GameRooms;

import java.util.ArrayList;
import java.util.List;


//TODO refactor this class
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
    private GameRoom gameRoom;
    private ConnectedPlayers connectedPlayers;
    private final GameConfigurer gameConfigurer;

    GameService() {
        this.gameRoom = null;
        this.connectedPlayers = new ConnectedPlayers(new ArrayList<>());
        this.gameConfigurer = new GameConfigurer();
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

    /**
     *
     * @param position of a shot on the board.
     * @return result of the shot.
     */
    Result shoot(String roomId, int position) {
        return gameRooms.findRoom(roomId).makeShot(position);
    }

    private ConnectionStatus createPlayerWaitingForOpponentStatus() {
        return new ConnectionStatus("No opponents for now",
                null,
                connectedPlayers.firstOneConnected().get().sessionId(), null,
                null, null,
                null, null,
                null, Event.CONNECT);
    }

    private ConnectionStatus createTwoPlayersConnectedStatus() {
        var gameSettings = gameConfigurer.createConfiguration(SHIP_SIZES_TO_BE_CREATED,
                BOARD_HEIGHT, BOARD_WIDTH, connectedPlayers.names(), connectedPlayers.ids());
        this.gameRoom = new GameRoom(gameSettings);
        var roomId = gameRooms.addRoom(gameRoom);
        //TODO refactor Optionals
         var connectionStatus = new ConnectionStatus("Players paired.",
                roomId,
                connectedPlayers.firstOneConnected().get().sessionId(),
                gameSettings.firstPlayersFleet().get().getFleetPositions(),
                connectedPlayers.secondOneConnected().get().sessionId(),
                gameSettings.secondPlayersFleet().get().getFleetPositions(),
                connectedPlayers.firstOneConnected().get().name(),
                connectedPlayers.firstOneConnected().get().name(),
                connectedPlayers.secondOneConnected().get().name(),
                Event.CONNECT);
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
            return new SurrenderResult(Event.SURRENDER, surrenderPlayerSessionId, winnerSessionId,
                    surrenderMessage, winnerMessage);
        } catch (IllegalArgumentException e) {
            return new SurrenderResult(Event.SURRENDER, surrenderPlayerSessionId, null,
                    surrenderMessage, winnerMessage);
        }
    }
}
