package org.wildhamsters.battleships;

import java.util.HashMap;
import java.util.Map;

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
        if (sections.containsKey(field)) {
            sections.put(field, ShipSectionCondition.DAMAGED);
        } else {
            throw new IllegalArgumentException();
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
