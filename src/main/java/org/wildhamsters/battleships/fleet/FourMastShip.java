package org.wildhamsters.battleships.fleet;

/**
 * @author Kevin Nowak
 */
final class FourMastShip extends Ship {
    FourMastShip(int firstField, int secondField, int thirdField, int fourthField) {
        super();
        sections.put(firstField, ShipSectionCondition.UNTOUCHED);
        sections.put(secondField, ShipSectionCondition.UNTOUCHED);
        sections.put(thirdField, ShipSectionCondition.UNTOUCHED);
        sections.put(fourthField, ShipSectionCondition.UNTOUCHED);
    }
}
