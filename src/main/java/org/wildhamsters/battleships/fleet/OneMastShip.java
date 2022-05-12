package org.wildhamsters.battleships.fleet;

/**
 * @author Kevin Nowak
 */
final class OneMastShip extends Ship {
    OneMastShip(int field) {
        super();
        sections.put(field, ShipSectionCondition.UNTOUCHED);
    }
}
