package org.wildhamsters.battleships;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Kevin Nowak
 */
public class FleetTests {
    @Test
    void testCheckIfAllShipsSunk1() {
        // Given
        Fleet fleet = new Fleet();
        // When
        boolean check = fleet.checkIfAllShipsSunk();
        // Then
        assertFalse(check);
    }

    @Test
    void testShotResult1() {
        // Given
        Fleet fleet = new Fleet();
        // Then
        assertEquals(fleet.makeShot(8), ShotResult.MISS);
    }

    @Test
    void testShotResult2() {
        // Given
        Fleet fleet = new Fleet();
        // Then
        assertEquals(fleet.makeShot(11), ShotResult.HIT);
    }

    @Test
    void testShotResult3() {
        // Given
        Fleet fleet = new Fleet();
        // When
        fleet.makeShot(11);
        fleet.makeShot(1);
        // Then
        assertEquals(fleet.makeShot(2), ShotResult.FLEET_SUNK);
    }

    @Test
    void testResetFleet1() {
        // Given
        Fleet fleet = new Fleet();
        // When
        fleet.makeShot(1);
        fleet.makeShot(2);
        fleet.makeShot(11);
        fleet.resetAllShipsToUntouched();
        // Then
        assertFalse(fleet.checkIfAllShipsSunk());
    }

    @Test
    void testResetFleet2() {
        // Given
        Fleet fleet = new Fleet();
        // When
        fleet.makeShot(1);
        fleet.makeShot(2);
        fleet.makeShot(11);
        fleet.resetAllShipsToUntouched();
        // Then
        assertTrue(fleet.fleetShips().stream().allMatch(ship ->
                ship.getShipCondition() == ShipCondition.UNTOUCHED
        ));
    }
}
