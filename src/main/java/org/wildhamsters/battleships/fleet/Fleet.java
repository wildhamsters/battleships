package org.wildhamsters.battleships.fleet;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Kevin Nowak
 */
@SuppressFBWarnings(
        value = "SF_SWITCH_NO_DEFAULT",
        justification = "Can't fix that for now"
)
public class Fleet {
    ShipsMap ships;
    FieldList allTakenFields;

    public Fleet() {
        ships = new ShipsMap();
        allTakenFields = new FieldList(List.of(new ShipPosition(List.of())));
    }

    public Fleet(ShipsPositions shipsPosition) {
        ships = new ShipsMap();
        allTakenFields = new FieldList(shipsPosition.getShipsPosition());
        putShipsIntoMap(allTakenFields.getList());
    }

    public Fleet(Fleet fleet) {
        this.ships = fleet.getShipsMap();
        this.allTakenFields = fleet.getAllTakenFields();
    }

    private FieldList getAllTakenFields() {
        return new FieldList(allTakenFields);
    }

    private ShipsMap getShipsMap() {
        return new ShipsMap(ships);
    }

    private void putShipsIntoMap(List<ShipPosition> allFieldLists) {
        for (ShipPosition shipPosition : allFieldLists) {
            switch (shipPosition.positions().size()) {
                case 1 -> putOneMastShip(shipPosition);
                case 2 -> putTwoMastShip(shipPosition);
                case 3 -> putThreeMastShip(shipPosition);
                case 4 -> putFourMastShip(shipPosition);
            }
        }
    }

    private void putOneMastShip(ShipPosition shipPosition) {
        ships.put(new OneMastShip(shipPosition.positions().get(0)), shipPosition);
    }

    private void putTwoMastShip(ShipPosition shipPosition) {
        ships.put(new TwoMastShip(shipPosition.positions().get(0), shipPosition.positions().get(1)), shipPosition);
    }

    private void putThreeMastShip(ShipPosition shipPosition) {
        ships.put(new ThreeMastShip(
                        shipPosition.positions().get(0),
                        shipPosition.positions().get(1),
                        shipPosition.positions().get(2)),
                shipPosition
        );
    }

    private void putFourMastShip(ShipPosition shipPosition) {
        ships.put(new FourMastShip(
                        shipPosition.positions().get(0),
                        shipPosition.positions().get(1),
                        shipPosition.positions().get(2),
                        shipPosition.positions().get(3)),
                shipPosition
        );
    }

    public ShotResult makeShot(int field) {
        AtomicBoolean tmp = new AtomicBoolean(false);

        allTakenFields.getList().forEach(shipPosition -> {
            if (shipPosition.positions().contains(field)) {
                tmp.set(true);
            }
        });

        if (!tmp.get()) {
            return ShotResult.MISS;
        } else {
            for (Map.Entry<Ship, ShipPosition> entry : ships.getEntrySet()) {
                if (entry.getValue().positions().contains(field)) {
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

    public List<Integer> getFleetPositions() {
        return ships.getKeySet()
                .stream()
                .flatMap(s -> s.sections.keySet().stream())
                .toList();
    }

    ShotResult makeShot(List<ShipPosition> fields) {
        fields.forEach(shipPosition -> {
            shipPosition.positions().forEach(this::makeShot);
        });
        return checkIfAllShipsSunk() ? ShotResult.FLEET_SUNK : ShotResult.HIT;
    }

    void resetAllShipsToUntouched() {
        for (Ship ship : ships.getKeySet()) {
            ship.resetToUntouched();
        }
    }

    public boolean checkIfAllShipsSunk() {
        AtomicBoolean answer = new AtomicBoolean(true);
        for (Ship ship : ships.getKeySet()) {
            if (ship.getShipCondition() != ShipCondition.SUNK) {
                answer.set(false);
            }
        }
        return answer.get();
    }


    public List<Integer> getSinkingShipPosition(int field) {
        if (makeShot(field) == ShotResult.SHIP_SUNK) {
            return ships.getShipPosition(field);
        }
        return List.of(field);
    }

    public boolean checkIfAllShipsUntouched() {
        AtomicBoolean answer = new AtomicBoolean(true);
        for (Ship ship : ships.getKeySet()) {
            if (ship.getShipCondition() != ShipCondition.UNTOUCHED) {
                answer.set(false);
            }
        }
        return answer.get();
    }

    public ShipCondition getShipConditionByIndex(int position) {
        if (!allTakenFields.getAllFieldsInOneList().contains(position)) {
            return ShipCondition.NO_SHIP_HERE;
        }
        return ships.getShipByPosition(position).getShipCondition();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fleet fleet = (Fleet) o;
        return Objects.equals(ships, fleet.ships) && Objects.equals(allTakenFields, fleet.allTakenFields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ships, allTakenFields);
    }

    @Override
    public String toString() {
        return "Fleet{" +
                "ships=" + ships +
                ", allTakenFields=" + allTakenFields +
                '}';
    }
}
