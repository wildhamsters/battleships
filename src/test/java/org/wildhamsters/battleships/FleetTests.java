package org.wildhamsters.battleships;

/**
 * @author Kevin Nowak
 */
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

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
        assertEquals(fleet.makeShot(6), ShotResult.HIT);
    }

    @Test
    void testShotResult3() {
        // Given
        Fleet fleet = new Fleet();
        // When
        fleet.makeShot(6);
        fleet.makeShot(1);
        // Then
        assertEquals(fleet.makeShot(2), ShotResult.FLEET_SUNK);
    }
}
