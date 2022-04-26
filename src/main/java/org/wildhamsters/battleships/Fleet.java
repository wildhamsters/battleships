package org.wildhamsters.battleships;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Kevin Nowak
 */
class Fleet {
    private final Map<Ship, List<Integer>> ships;
    FieldList allTakenFields;

    Fleet() {
        ships = new HashMap<>();
        allTakenFields = new FieldList(List.of(List.of()));
    }

    Fleet(List<List<Integer>> list) {
        ships = new HashMap<>();
        allTakenFields = new FieldList(list);
        putShipsIntoMap(allTakenFields.allFieldLists());
    }

    private void putShipsIntoMap(List<List<Integer>> allFieldLists) {
        for (List<Integer> fieldList : allFieldLists) {
            switch (fieldList.size()) {
                case 1 -> putOneMastShip(fieldList);
                case 2 -> putTwoMastShip(fieldList);
                case 3 -> putThreeMastShip(fieldList);
                case 4 -> putFourMastShip(fieldList);
            }
        }
    }

    private void putOneMastShip(List<Integer> fieldList) {
        ships.put(new OneMastShip(fieldList.get(0)), fieldList);
    }

    private void putTwoMastShip(List<Integer> fieldList) {
        ships.put(new TwoMastShip(fieldList.get(0), fieldList.get(1)), fieldList);
    }

    private void putThreeMastShip(List<Integer> fieldList) {
        ships.put(new ThreeMastShip(fieldList.get(0), fieldList.get(1), fieldList.get(2)), fieldList);
    }

    private void putFourMastShip(List<Integer> fieldList) {
        ships.put(new FourMastShip(fieldList.get(0), fieldList.get(1), fieldList.get(2), fieldList.get(3)), fieldList);
    }

    ShotResult makeShot(int field) {
        if (!allTakenFields.contains(field)) {
            return ShotResult.MISS;
        } else {
            for (Map.Entry<Ship, List<Integer>> entry : ships.entrySet()) {
                if (entry.getValue().contains(field)) {
                    entry.getKey().markHit(field);
                    if (entry.getKey().getShipCondition() == ShipCondition.SUNK) {
                        return ShotResult.SHIP_SUNK;
                    }
                }
            }
        }
        if (checkIfAllShipsSunk()) {
            return ShotResult.FLEET_SUNK;
        }
        return ShotResult.HIT;
    }

    ShotResult makeShot(List<List<Integer>> fields) {
        fields.forEach(list -> {
            list.forEach(this::makeShot);
        });
        return checkIfAllShipsSunk() ? ShotResult.FLEET_SUNK : ShotResult.HIT;
    }

    void resetAllShipsToUntouched() {
        for (Ship ship : ships.keySet()) {
            ship.resetToUntouched();
        }
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

    Set<Ship> fleetShips() {
        return ships.keySet();
    }
}
