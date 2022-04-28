package org.wildhamsters.battleships.fleet;

/**
 * @author Kevin Nowak
 */
class FourMastShip extends Ship {
    private final int length = 4;

    FourMastShip(int firstField, int secondField, int thirdField, int fourthField) {
        super();
        sections.put(firstField, ShipSectionCondition.UNTOUCHED);
        sections.put(secondField, ShipSectionCondition.UNTOUCHED);
        sections.put(thirdField, ShipSectionCondition.UNTOUCHED);
        sections.put(fourthField, ShipSectionCondition.UNTOUCHED);
    }
}
