package org.wildhamsters.battleships.fleet;

import org.wildhamsters.battleships.board.FieldState;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kevin Nowak
 */
class FieldList {
    List<ShipPosition> allFieldLists;

    public FieldList(List<ShipPosition> allFieldLists) {
        this.allFieldLists = allFieldLists;
    }

    public FieldList(FieldList fieldList) {
        this.allFieldLists = new ArrayList<>(fieldList.getList());
    }

    public List<ShipPosition> getList() {
        return this.allFieldLists;
    }

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
