package org.wildhamsters.battleships;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * @author Kevin Nowak
 */
public class FleetTests {
    @DataProvider(name = "fieldList-provider")
    public Object[][] fieldListProvider() {
        return new Object[][]{
                {
                        new ArrayList<>() {{
                            add(List.of(1));
                            add(List.of(10));
                            add(List.of(91));
                            add(List.of(100));
                            add(List.of(3, 4));
                            add(List.of(6, 7));
                            add(List.of(93, 94));
                            add(List.of(23, 24, 25));
                            add(List.of(43, 44, 45));
                            add(List.of(63, 64, 65, 66));
                        }}
                }
        };
    }

    @Test(dataProvider = "fieldList-provider")
    void testNewFleetNotAllShipsSunk(List<List<Integer>> data) {
        // Given
        Fleet fleet = new Fleet(data);
        // When
        boolean check = fleet.checkIfAllShipsSunk();
        // Then
        assertFalse(check);
    }

    @Test(dataProvider = "fieldList-provider")
    void testMakeShotWithResultMiss(List<List<Integer>> data) {
        // Given
        Fleet fleet = new Fleet(data);
        // When
        ShotResult shotResult = fleet.makeShot(8);
        // Then
        assertEquals(shotResult, ShotResult.MISS);
    }

    @Test(dataProvider = "fieldList-provider")
    void testMakeShotWithResultHit(List<List<Integer>> data) {
        // Given
        Fleet fleet = new Fleet(data);
        // When
        ShotResult shotResult = fleet.makeShot(3);
        // Then
        assertEquals(shotResult, ShotResult.HIT);
    }

    @Test(dataProvider = "fieldList-provider")
    void testMakeShotWithResultShipSunk(List<List<Integer>> data) {
        // Given
        Fleet fleet = new Fleet(data);
        // When
        ShotResult shotResult = fleet.makeShot(1);
        // Then
        assertEquals(shotResult, ShotResult.SHIP_SUNK);
    }

    @Test(dataProvider = "fieldList-provider")
    void testMakeShotsWithResultFleetSunk(List<List<Integer>> data) {
        // Given
        Fleet fleet = new Fleet(data);
        // When
        ShotResult shotResult = fleet.makeShot(data);
        // Then
        assertEquals(shotResult, ShotResult.FLEET_SUNK);
    }

    @Test(dataProvider = "fieldList-provider")
    void testFleetReset(List<List<Integer>> data) {
        // Given
        SoftAssert softAssert = new SoftAssert();
        Fleet fleet = new Fleet(data);
        // When
        fleet.makeShot(data);
        softAssert.assertFalse(fleet.checkIfAllShipsUntouched());
        fleet.resetAllShipsToUntouched();
        // Then
        softAssert.assertTrue(fleet.checkIfAllShipsUntouched());
        softAssert.assertAll();
    }
}
