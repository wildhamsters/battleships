package org.wildhamsters.battleships;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test
public class GameServiceTest {

    @Test
    void shouldReturnProperConnectionStatus_whenFirstPlayerConnects() {
        //given
        var gameService = new GameService();
        var expected = new ConnectionStatus("No opponents for now",
                null,
                "ZbychSessionId", null,
                null, null,
                null, null,
                null, Event.CONNECT);
        //when
        var actual = gameService.processConnectingPlayers(
                new ConnectedPlayer("Zbych", "ZbychSessionId"));
        //then
        assertEquals(actual, expected);
    }
}
