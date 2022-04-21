package org.wildhamsters.battleships;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.testng.Assert.assertEquals;

public class ShipTests {

    @Test
    void testOneMastShipToString() {
        // Given
        OneMastShip one = new OneMastShip();
        // When
        String expected = "[ ]";
        // Then
        assertEquals(one.toString(), expected);
    }

    @Test
    void testTwoMastShipToString() {
        // Given
        TwoMastShip twoMastShip = new TwoMastShip();
        // When
        String expected = "[ ][ ]";
        // Then
        assertEquals(twoMastShip.toString(), expected);
    }

    @Test
    void testMarkingHit() {
        // Given
        TwoMastShip twoMastShip = new TwoMastShip();
        // When
        twoMastShip.markHit(1);
        // Then
        assertEquals(twoMastShip.toString(), "[X][ ]");
    }

    @Test
    void testGettingShipConditionOneMastShip() {
        // Given
        OneMastShip untouched_ship = new OneMastShip();
        OneMastShip sunk_ship = new OneMastShip();
        // When
        sunk_ship.markHit(1);
        // Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(untouched_ship.getShipCondition(), ShipCondition.UNTOUCHED);
        softAssert.assertEquals(sunk_ship.getShipCondition(), ShipCondition.SUNK);
    }

    @Test
    void testGettingShipConditionTwoMastShip() {
        // Given
        TwoMastShip untouched_ship = new TwoMastShip();
        TwoMastShip hit_ship = new TwoMastShip();
        TwoMastShip sunk_ship = new TwoMastShip();
        // When
        hit_ship.markHit(1);
        sunk_ship.markHit(1);
        sunk_ship.markHit(2);
        // Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(untouched_ship.getShipCondition(), ShipCondition.UNTOUCHED);
        softAssert.assertEquals(hit_ship.getShipCondition(), ShipCondition.HIT);
        softAssert.assertEquals(sunk_ship.getShipCondition(), ShipCondition.SUNK);
    }
}
