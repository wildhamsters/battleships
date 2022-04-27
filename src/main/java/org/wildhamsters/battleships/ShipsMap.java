package org.wildhamsters.battleships;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Kevin Nowak
 */
class ShipsMap {
    Map<Ship, List<Integer>> ships;

    ShipsMap() {
        ships = new HashMap<>();
    }

    void put(Ship ship, List<Integer> list) {
        ships.put(ship, list);
    }

    Set<Map.Entry<Ship, List<Integer>>> getEntrySet() {
        return ships.entrySet();
    }

    Set<Ship> getKeySet() {
      return ships.keySet();
    }
}
