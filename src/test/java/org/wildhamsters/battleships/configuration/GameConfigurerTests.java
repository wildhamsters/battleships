package org.wildhamsters.battleships.configuration;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.wildhamsters.battleships.board.Board;
import org.wildhamsters.battleships.fleet.Fleet;
import org.wildhamsters.battleships.fleet.FleetTests;
import org.wildhamsters.battleships.fleet.ShipPosition;
import org.wildhamsters.battleships.fleet.ShipsPositions;
import org.wildhamsters.battleships.play.GameRoom;

import java.util.List;
import java.util.Optional;

public class GameConfigurerTests {
    @Test(dataProvider = "fieldListProvider", dataProviderClass = FleetTests.class)
    public void testGameSettingsOptionalMethods(List<ShipPosition> data) {
        // Given
        Fleet testFleet = new Fleet(new ShipsPositions(data));
        Board testBoard = Board.create(new ShipsPositions(data));
        GameSettings testSettings = new GameSettings(createPlayerSettingsList(testFleet, testBoard));
        // When
        Optional<Fleet> p1Fleet = testSettings.firstPlayersFleet();
        Optional<Fleet> p2Fleet = testSettings.secondPlayersFleet();
        // Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(p1Fleet.isPresent());
        softAssert.assertTrue(p2Fleet.isPresent());
        softAssert.assertEquals(p1Fleet.get(), testFleet);
        softAssert.assertEquals(p2Fleet.get(), testFleet);
        softAssert.assertEquals(testSettings.playerSettings(), createPlayerSettingsList(testFleet, testBoard));

        softAssert.assertAll();
    }

    @Test(dataProvider = "fieldListProvider", dataProviderClass = FleetTests.class)
    public void testGameSettingsOptionalMethodsWithEmptyPlayerList(List<ShipPosition> data) {
        // Given
        GameSettings testSettings = new GameSettings(List.of());
        // When
        Optional<Fleet> p1Fleet = testSettings.firstPlayersFleet();
        Optional<Fleet> p2Fleet = testSettings.secondPlayersFleet();
        // Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(p1Fleet.isEmpty());
        softAssert.assertTrue(p2Fleet.isEmpty());

        softAssert.assertAll();
    }

    static List<GameSettings.PlayerSettings> createPlayerSettingsList(Fleet fleet, Board board) {
        GameSettings.PlayerSettings ps1 = new GameSettings.PlayerSettings(
                "001",
                "Player1",
                board,
                fleet
        );

        GameSettings.PlayerSettings ps2 = new GameSettings.PlayerSettings(
                "002",
                "Player2",
                board,
                fleet
        );

        return List.of(ps1, ps2);
    }
}
