package org.wildhamsters.battleships;

interface Ship {
    Ship markHit(int index);
    ShipCondition getShipCondition();
}
