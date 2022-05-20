package org.wildhamsters.battleships;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import org.wildhamsters.battleships.play.MatchStatisticsRepository;

import static org.testng.Assert.assertEquals;

@Test
public class GameServiceTest {

    @Autowired
    private MatchStatisticsRepository matchStatisticsRepository;

    @Test
    void shouldReturnProperConnectionStatus_whenFirstPlayerConnects() {
        //given
        var gameService = new GameService(matchStatisticsRepository);
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
