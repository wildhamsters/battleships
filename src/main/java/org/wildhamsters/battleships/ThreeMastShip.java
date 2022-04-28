package org.wildhamsters.battleships;

/**
 * @author Kevin Nowak
 */
class ThreeMastShip extends Ship {
    private final int length = 3;

    ThreeMastShip(int firstField, int secondField, int thirdField) {
        super();
        sections.put(firstField, ShipSectionCondition.UNTOUCHED);
        sections.put(secondField, ShipSectionCondition.UNTOUCHED);
        sections.put(thirdField, ShipSectionCondition.UNTOUCHED);
    }
}
