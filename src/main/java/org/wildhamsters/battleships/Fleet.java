package org.wildhamsters.battleships;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

class Fleet {
    // Contains for now 1x OneMastShip and 1x TwoMastShip
    ArrayList<Ship> ships;

    Fleet() {
        ships = new ArrayList<>();
        ships.add(new OneMastShip(6));
        ships.add(new TwoMastShip(1, 2));
    }

    // TODO: check if any ship has the field
    
    boolean checkIfAllShipsSunk() {
        AtomicBoolean answer = new AtomicBoolean(true);
        ships.forEach(elem -> {
            if (elem.getShipCondition() != ShipCondition.SUNK) {
                answer.set(false);
            }
        });
        return answer.get();
    }
}
