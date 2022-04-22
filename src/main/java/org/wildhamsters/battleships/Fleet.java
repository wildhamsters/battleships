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
        ships.put(new OneMastShip(11), List.of(11));
        ships.put(new TwoMastShip(1, 2), List.of(1, 2));
        occupiedFields = List.of(1, 2, 11);
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

    Fleet resetAllShipsToUntouched() {
        for (Ship ship : ships.keySet()) {
            ship.resetToUntouched();
        }
        return this;
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
