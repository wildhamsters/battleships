package org.wildhamsters.battleships.fleet;

/**
 * @author Kevin Nowak
 */
final class TwoMastShip extends Ship {
    private final int length = 2;

    TwoMastShip(int firstField, int secondField) {
        super();
        sections.put(firstField, ShipSectionCondition.UNTOUCHED);
        sections.put(secondField, ShipSectionCondition.UNTOUCHED);
    }
}
