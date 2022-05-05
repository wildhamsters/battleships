package org.wildhamsters.battleships;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Collection of connected players.
 *
 * @author Dominik Żebracki
 */
class ConnectedPlayers {

    private final List<ConnectedPlayer> players;

    ConnectedPlayers(List<ConnectedPlayer> players) {
        this.players = players;
    }

    Optional<ConnectedPlayer> firstOneConnected() {
        return players.size() > 0 ? Optional.of(players.get(0)) : Optional.empty();
    }

    Optional<ConnectedPlayer> secondOneConnected() {
        return players.size() > 1 ? Optional.of(players.get(1)) : Optional.empty();
    }

    List<String> names() {
        return players.stream().map(ConnectedPlayer::name).toList();
    }

    List<String> ids() {
        return players.stream().map(ConnectedPlayer::sessionId).toList();
    }

    ConnectedPlayers add(ConnectedPlayer player) {
        var afterAdding = new ArrayList<>(players);
        afterAdding.add(player);
        return new ConnectedPlayers(afterAdding);
    }

    boolean areBothConnected() {
        return players.size() == 2;
    }
}
