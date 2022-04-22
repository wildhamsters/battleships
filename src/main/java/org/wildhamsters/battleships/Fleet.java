package org.wildhamsters.battleships;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Kevin Nowak
 */
class Fleet {
    Map<Ship, List<Integer>> ships;
    List<Integer> occupiedFields;

    Fleet() {
        ships = new HashMap<>();
        ships.put(new OneMastShip(6), List.of(6));
        ships.put(new TwoMastShip(1, 2), List.of(1, 2));
        occupiedFields = List.of(1, 2, 6);
    }

    ShotResult makeShot(int field) {
        if (!occupiedFields.contains(field)) {
            return ShotResult.MISS;
        } else {
            for (Map.Entry<Ship, List<Integer>> entry : ships.entrySet()) {
                if (entry.getValue().contains(field)) {
                    entry.getKey().markHit(field);
                }
            }
        }
        if (checkIfAllShipsSunk()) {
            return ShotResult.FLEET_SUNK;
        }
        return ShotResult.HIT;
    }

    boolean checkIfAllShipsSunk() {
        AtomicBoolean answer = new AtomicBoolean(true);
        for (Ship ship : ships.keySet()) {
            if (ship.getShipCondition() != ShipCondition.SUNK) {
                answer.set(false);
            }
        }
        return answer.get();
    }
}
