package org.wildhamsters.battleships;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kevin Nowak
 */
class OneMastShip extends Ship {
    private final int length = 1;

    OneMastShip(int field) {
        super();
        sections.put(field, ShipSectionCondition.UNTOUCHED);
    }
}
