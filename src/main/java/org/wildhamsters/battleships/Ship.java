package org.wildhamsters.battleships;

/**
 * @author Kevin Nowak
 */
interface Ship {
    Ship markHit(int index);
    ShipCondition getShipCondition();
    Ship resetToUntouched();
}
