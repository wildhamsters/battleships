package org.wildhamsters.battleships;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kevin Nowak
 */
class TwoMastShip extends Ship {
    private final int length = 2;

    TwoMastShip(int firstField, int secondField) {
        super();
        sections.put(firstField, ShipSectionCondition.UNTOUCHED);
        sections.put(secondField, ShipSectionCondition.UNTOUCHED);
    }
}
