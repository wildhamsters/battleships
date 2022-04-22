package org.wildhamsters.battleships;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kevin Nowak
 */
class TwoMastShip implements Ship {
    private final int length = 2;
    private Map<Integer, ShipSectionCondition> sections;
    private ShipCondition condition;

    TwoMastShip(int firstField, int secondField) {
        sections = new HashMap<>();
        sections.put(firstField, ShipSectionCondition.UNTOUCHED);
        sections.put(secondField, ShipSectionCondition.UNTOUCHED);
        condition = ShipCondition.UNTOUCHED;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<Integer, ShipSectionCondition> entry : sections.entrySet()) {
            sb.append("[");
            if (entry.getValue() == ShipSectionCondition.UNTOUCHED) {
                sb.append(" ");
            } else {
                sb.append("X");
            }
            sb.append("]");
        }
        return sb.toString();
    }

    @Override
    public Ship markHit(int field) {
        try {
            if (sections.containsKey(field)) {
                sections.put(field, ShipSectionCondition.DAMAGED);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            System.err.println("Ship does not contain section on given field");
        }
        if (condition != ShipCondition.HIT) {
            condition = ShipCondition.HIT;
        }
        return this;
    }

    @Override
    public ShipCondition getShipCondition() {
        return verifyCondition();
    }

    private ShipCondition verifyCondition() {
        if (sections.values().stream().allMatch(x -> (x == ShipSectionCondition.DAMAGED))) {
            condition = ShipCondition.SUNK;
        } else if (sections.values().contains(ShipSectionCondition.DAMAGED)) {
            condition = ShipCondition.HIT;
        } else {
            condition = ShipCondition.UNTOUCHED;
        }
        return condition;
    }
}
