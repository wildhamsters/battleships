package org.wildhamsters.battleships;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kevin Nowak
 */
class Ship {
    Map<Integer, ShipSectionCondition> sections;
    private ShipCondition condition;

    public Ship() {
        sections = new HashMap<>();
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

    void markHit(int field) {
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
    }

    ShipCondition getShipCondition() {
        return verifyCondition();
    }

    private ShipCondition verifyCondition() {
        if (sections.values().stream().allMatch(x -> (x == ShipSectionCondition.DAMAGED))) {
            condition = ShipCondition.SUNK;
        } else if (sections.containsValue(ShipSectionCondition.DAMAGED)) {
            condition = ShipCondition.HIT;
        } else {
            condition = ShipCondition.UNTOUCHED;
        }
        return condition;
    }

    void resetToUntouched() {
        sections.replaceAll((k, v) -> v = ShipSectionCondition.UNTOUCHED);
        condition = ShipCondition.UNTOUCHED;
    }

}
