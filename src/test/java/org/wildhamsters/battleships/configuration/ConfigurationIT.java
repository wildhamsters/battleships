package org.wildhamsters.battleships.configuration;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class ConfigurationIT {

    @Test
    public void testCreateConfiguration() {
        GameSettings settings = new GameConfigurer("https://protected-stream-19238.herokuapp.com/placeShips").createConfiguration(List.of(1, 1, 1, 2, 2, 3),
                10, 10, List.of("Player1", "Player2"), List.of("id1", "id2"));

        GameSettings.PlayerSettings firstPlayerSettings = settings.getFirstPlayerSettings();
        GameSettings.PlayerSettings secondPlayerSettings = settings.getSecondPlayerSettings();

        SoftAssert sa = new SoftAssert();
        sa.assertEquals(firstPlayerSettings.getName(), "Player1");
        sa.assertEquals(secondPlayerSettings.getName(), "Player2");

        sa.assertEquals(firstPlayerSettings.getId(), "id1");
        sa.assertEquals(secondPlayerSettings.getId(), "id2");

        sa.assertAll();
    }

}