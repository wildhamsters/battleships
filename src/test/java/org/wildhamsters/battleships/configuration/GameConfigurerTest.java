package org.wildhamsters.battleships.configuration;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;


public class GameConfigurerTest {

    @Test
    public void testCreateConfiguration() {
        GameSettings settings = new GameConfigurer().createConfiguration(List.of(1, 1, 1, 2, 2, 3),
                10, 10, List.of("Player1", "Player2"), List.of("id1", "id2"));

        List<GameSettings.PlayerSettings> playersSettings = settings.playerSettings();
        GameSettings.PlayerSettings firstPlayerSettings = playersSettings.get(0);
        GameSettings.PlayerSettings secondPlayerSettings = playersSettings.get(1);

        SoftAssert sa = new SoftAssert();
        sa.assertEquals(firstPlayerSettings.name(), "Player1");
        sa.assertEquals(secondPlayerSettings.name(), "Player2");

        sa.assertEquals(firstPlayerSettings.id(), "id1");
        sa.assertEquals(secondPlayerSettings.id(), "id2");

        sa.assertAll();
    }


}