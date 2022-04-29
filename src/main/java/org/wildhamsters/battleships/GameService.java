package org.wildhamsters.battleships;

import org.springframework.stereotype.Service;
import org.wildhamsters.battleships.configuration.GameConfigurer;
import org.wildhamsters.battleships.play.GameRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dominik Żebracki
 */
@Service
class GameService {

    private GameRoom gameRoom;
    private ConnectedPlayers connectedPlayers;
    private final GameConfigurer gameConfigurer;

    GameService(GameRoom gameRoom, ConnectedPlayers connectedPlayers, GameConfigurer gameConfigurer) {
        this.gameRoom = gameRoom;
        this.connectedPlayers = connectedPlayers;
        this.gameConfigurer = gameConfigurer;
    }

    GameService() {
        this.gameRoom = null;
        this.connectedPlayers = new ConnectedPlayers(new ArrayList<>());
        this.gameConfigurer = new GameConfigurer();
    }

    ConnectionStatus processConnectingPlayers(ConnectedPlayer connectedPlayer) {
        connectedPlayers = connectedPlayers.add(connectedPlayer);
        if(!connectedPlayers.areBothConnected()) {
            return new ConnectionStatus("No opponents for now",
                    null, null,
                    null, null,
                    null, Event.CONNECT);
        } else {
            var gameSettings = gameConfigurer.createConfiguration(List.of(4, 3, 3, 2, 2, 2, 1, 1, 1, 1)
                    ,10, 10, connectedPlayers.names(), connectedPlayers.ids());
            this.gameRoom = new GameRoom(gameSettings);
            var connectionStatus = new ConnectionStatus("Players paired.",
                    connectedPlayers.firstOneConnected().get().sessionId(),
                    gameSettings.firstPlayersFleet().get().getFleetPositions(),
                    connectedPlayers.secondOneConnected().get().sessionId(),
                    gameSettings.secondPlayersFleet().get().getFleetPositions(),
                    connectedPlayers.firstOneConnected().get().name(),
                    Event.CONNECT);
            System.out.println(connectionStatus);
            System.out.println(gameRoom);
            return connectionStatus;
        }
    }

    Result shoot(int position) {
        return gameRoom.makeShot(position);
    }
}
