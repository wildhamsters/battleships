package org.wildhamsters.battleships.fleet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kevin Nowak
 */
record FieldList(List<ShipPosition> allFieldLists) {

    boolean contains(int field) {
        return allFieldLists.stream().allMatch(shipPosition -> shipPosition
                .positions()
                .stream()
                .allMatch(integer -> integer != field));
    }

    List<Integer> getAllFieldsInOneList() {
        List<Integer> ret = new ArrayList<>();
        allFieldLists.forEach(shipPosition -> ret.addAll(shipPosition.positions()));
        return ret;
    }
}
