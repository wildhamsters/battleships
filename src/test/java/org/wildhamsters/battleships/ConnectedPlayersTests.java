package org.wildhamsters.battleships;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ConnectedPlayersTests {
    @Test
    void shouldReturnTrue() {
        List<ConnectedPlayer> playerList = new ArrayList<>();
        ConnectedPlayers connectedPlayers = new ConnectedPlayers(playerList);
        connectedPlayers = connectedPlayers.add(new ConnectedPlayer("playerOne", "idRoom"));
        connectedPlayers = connectedPlayers.add(new ConnectedPlayer("playerTwo", "idRooom"));
        assertTrue(connectedPlayers.areBothConnected());
    }

    @Test
    void shouldReturnProperPlayersInfo() {
        List<ConnectedPlayer> playerList = new ArrayList<>();
        ConnectedPlayers connectedPlayers = new ConnectedPlayers(playerList);
        connectedPlayers = connectedPlayers.add(new ConnectedPlayer("playerOne", "idRoom"));
        connectedPlayers = connectedPlayers.add(new ConnectedPlayer("playerTwo", "idRoom"));
        SoftAssert sa = new SoftAssert();

        List<String> playersNames = connectedPlayers.names();
        sa.assertTrue(playersNames.contains("playerOne"));
        sa.assertTrue(playersNames.contains("playerTwo"));
        sa.assertEquals(connectedPlayers.ids(), List.of("idRoom","idRoom"));
        sa.assertAll();

    }
}
