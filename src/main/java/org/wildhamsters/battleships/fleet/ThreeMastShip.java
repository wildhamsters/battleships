package org.wildhamsters.battleships.fleet;

/**
 * @author Kevin Nowak
 */
final class ThreeMastShip extends Ship {
    private final int length = 3;

    ThreeMastShip(int firstField, int secondField, int thirdField) {
        super();
        sections.put(firstField, ShipSectionCondition.UNTOUCHED);
        sections.put(secondField, ShipSectionCondition.UNTOUCHED);
        sections.put(thirdField, ShipSectionCondition.UNTOUCHED);
    }
}
