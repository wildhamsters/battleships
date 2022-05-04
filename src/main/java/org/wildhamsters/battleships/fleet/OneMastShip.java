package org.wildhamsters.battleships.fleet;

/**
 * @author Kevin Nowak
 */
final class OneMastShip extends Ship {
    private final int length = 1;

    OneMastShip(int field) {
        super();
        sections.put(field, ShipSectionCondition.UNTOUCHED);
    }
}
