package org.wildhamsters.battleships;

import java.util.*;

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
        shufflePlayers(afterAdding);
        return new ConnectedPlayers(afterAdding);
    }

    private void shufflePlayers(List<ConnectedPlayer> afterAdding) {
        Collections.shuffle(afterAdding);
    }

    boolean areBothConnected() {
        return players.size() == 2;
    }
}
