package org.wildhamsters.battleships;

import java.util.ArrayList;

class Fleet {
    // Contains for now 1x OneMastShip and 1x TwoMastShip
    ArrayList<Ship> ships;

    Fleet() {
        ships = new ArrayList<>();
        ships.add(new OneMastShip(6));
        ships.add(new TwoMastShip(1, 2));
    }

    // TODO: checkIfAllShipsAreSunk()
}
