package org.wildhamsters.battleships.fleet;

import java.util.*;

/**
 * @author Kevin Nowak
 */
class ShipsMap {
    private final Map<Ship, ShipPosition> ships;

    ShipsMap() {
        ships = new HashMap<>();
    }

    void put(Ship ship, ShipPosition shipPosition) {
        ships.put(ship, shipPosition);
    }

    Set<Map.Entry<Ship, ShipPosition>> getEntrySet() {
        return ships.entrySet();
    }

    Set<Ship> getKeySet() {
        return ships.keySet();
    }

    Ship getShipByPosition(int position) {
        for (Map.Entry<Ship, ShipPosition> entry : ships.entrySet()) {
            if (entry.getValue().positions().contains(position)) {
                return entry.getKey();
            }
        }
        return null;
    }

    List<Integer> getShipPosition(int field) {
        for (var entry : ships.entrySet()) {
            if (entry.getValue().positions().contains(field)) {
                return entry.getValue().positions();
            }
        }
        return Collections.emptyList();
    }
}
