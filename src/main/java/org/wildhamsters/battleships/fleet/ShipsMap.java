package org.wildhamsters.battleships.fleet;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
}
