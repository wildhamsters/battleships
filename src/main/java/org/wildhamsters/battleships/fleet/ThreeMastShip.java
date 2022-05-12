package org.wildhamsters.battleships.fleet;

/**
 * @author Kevin Nowak
 */
final class ThreeMastShip extends Ship {
    ThreeMastShip(int firstField, int secondField, int thirdField) {
        super();
        sections.put(firstField, ShipSectionCondition.UNTOUCHED);
        sections.put(secondField, ShipSectionCondition.UNTOUCHED);
        sections.put(thirdField, ShipSectionCondition.UNTOUCHED);
    }
}
