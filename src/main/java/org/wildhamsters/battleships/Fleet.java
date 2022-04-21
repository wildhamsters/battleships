package org.wildhamsters.battleships;

import java.util.ArrayList;

class Fleet {
    // Contains for now 1x OneMastShip and 1x TwoMastShip
    ArrayList<Ship> ships;

    Fleet() {
        ships = new ArrayList<>();
        ships.add(new OneMastShip());
        ships.add(new TwoMastShip());
    }
}
