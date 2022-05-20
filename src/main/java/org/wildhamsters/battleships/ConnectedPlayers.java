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

    ConnectedPlayer firstOneConnected() {
        return players.get(0);
    }

    ConnectedPlayer secondOneConnected() {
        return players.get(1);
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
