package org.wildhamsters.battleships.fleet;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.wildhamsters.battleships.fleet.OneMastShip;
import org.wildhamsters.battleships.fleet.ShipCondition;
import org.wildhamsters.battleships.fleet.TwoMastShip;

import static org.testng.Assert.assertEquals;

public class ShipTests {

    @Test
    void testOneMastShipToString() {
        // Given
        OneMastShip one = new OneMastShip(1);
        // When
        String expected = "[ ]";
        // Then
        assertEquals(one.toString(), expected);
    }

    @Test
    void testTwoMastShipToString() {
        // Given
        TwoMastShip twoMastShip = new TwoMastShip(2, 3);
        // When
        String expected = "[ ][ ]";
        // Then
        assertEquals(twoMastShip.toString(), expected);
    }

    @Test
    void testMarkingHitOneMastShip() {
        // Given
        OneMastShip oneMastShip = new OneMastShip(15);
        // When
        oneMastShip.markHit(15);
        // Then
        assertEquals(oneMastShip.toString(), "[X]");
    }

    @Test
    void testMarkingHitTwoMastShip() {
        // Given
        TwoMastShip twoMastShip = new TwoMastShip(1, 2);
        // When
        twoMastShip.markHit(1);
        // Then
        assertEquals(twoMastShip.toString(), "[X][ ]");
    }

    @Test
    void testGettingShipConditionOneMastShip() {
        // Given
        OneMastShip untouched_ship = new OneMastShip(6);
        OneMastShip sunk_ship = new OneMastShip(1);
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
        TwoMastShip untouched_ship = new TwoMastShip(1, 2);
        TwoMastShip hit_ship = new TwoMastShip(6, 7);
        TwoMastShip sunk_ship = new TwoMastShip(11, 12);
        // When
        hit_ship.markHit(6);
        sunk_ship.markHit(11);
        sunk_ship.markHit(12);
        // Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(untouched_ship.getShipCondition(), ShipCondition.UNTOUCHED);
        softAssert.assertEquals(hit_ship.getShipCondition(), ShipCondition.HIT);
        softAssert.assertEquals(sunk_ship.getShipCondition(), ShipCondition.SUNK);
        softAssert.assertAll();
    }
}
