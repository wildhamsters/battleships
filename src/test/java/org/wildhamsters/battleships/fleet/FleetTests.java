package org.wildhamsters.battleships.fleet;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.wildhamsters.battleships.fleet.Fleet;
import org.wildhamsters.battleships.fleet.ShipPosition;
import org.wildhamsters.battleships.fleet.ShipsPositions;
import org.wildhamsters.battleships.fleet.ShotResult;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class FleetTests {
    @DataProvider(name = "fieldList-provider")
    public Object[][] fieldListProvider() {
        return new Object[][]{
                {
                        new ArrayList<>() {{
                            add(new ShipPosition(List.of(1)));
                            add(new ShipPosition(List.of(10)));
                            add(new ShipPosition(List.of(91)));
                            add(new ShipPosition(List.of(100)));
                            add(new ShipPosition(List.of(3, 4)));
                            add(new ShipPosition(List.of(6, 7)));
                            add(new ShipPosition(List.of(93, 94)));
                            add(new ShipPosition(List.of(23, 24, 25)));
                            add(new ShipPosition(List.of(43, 44, 45)));
                            add(new ShipPosition(List.of(63, 64, 65, 66)));
                        }}
                }
        };
    }

    @Test(dataProvider = "fieldList-provider")
    void testNewFleetNotAllShipsSunk(List<ShipPosition> data) {
        // Given
        Fleet fleet = new Fleet(new ShipsPositions(data));
        // When
        boolean check = fleet.checkIfAllShipsSunk();
        // Then
        assertFalse(check);
    }

    @Test(dataProvider = "fieldList-provider")
    void testMakeShotWithResultMiss(List<ShipPosition> data) {
        // Given
        Fleet fleet = new Fleet(new ShipsPositions(data));
        // When
        ShotResult shotResult = fleet.makeShot(8);
        // Then
        assertEquals(shotResult, ShotResult.MISS);
    }

    @Test(dataProvider = "fieldList-provider")
    void testMakeShotWithResultHit(List<ShipPosition> data) {
        // Given
        Fleet fleet = new Fleet(new ShipsPositions(data));
        // When
        ShotResult shotResult = fleet.makeShot(3);
        // Then
        assertEquals(shotResult, ShotResult.HIT);
    }

    @Test(dataProvider = "fieldList-provider")
    void testMakeShotWithResultShipSunk(List<ShipPosition> data) {
        // Given
        Fleet fleet = new Fleet(new ShipsPositions(data));
        // When
        ShotResult shotResult = fleet.makeShot(1);
        // Then
        assertEquals(shotResult, ShotResult.SHIP_SUNK);
    }

    @Test(dataProvider = "fieldList-provider")
    void testMakeShotsWithResultFleetSunk(List<ShipPosition> data) {
        // Given
        Fleet fleet = new Fleet(new ShipsPositions(data));
        // When
        ShotResult shotResult = fleet.makeShot(data);
        // Then
        assertEquals(shotResult, ShotResult.FLEET_SUNK);
    }

    @Test(dataProvider = "fieldList-provider")
    void testFleetReset(List<ShipPosition> data) {
        // Given
        SoftAssert softAssert = new SoftAssert();
        Fleet fleet = new Fleet(new ShipsPositions(data));
        // When
        fleet.makeShot(data);
        softAssert.assertFalse(fleet.checkIfAllShipsUntouched());
        fleet.resetAllShipsToUntouched();
        // Then
        softAssert.assertTrue(fleet.checkIfAllShipsUntouched());
        softAssert.assertAll();
    }
}
