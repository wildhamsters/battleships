package org.wildhamsters.battleships;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kevin Nowak
 */
record FieldList(List<List<Integer>> allFieldLists) {

    boolean contains(int field) {
        for (List<Integer> fieldList : allFieldLists) {
            if (fieldList.contains(field)) {
                return true;
            }
        }
        return false;
    }

    List<Integer> getAllFieldsInOneList() {
        List<Integer> ret = new ArrayList<>();
        for (List<Integer> fieldList : allFieldLists) {
            ret.addAll(fieldList);
        }
        return ret;
    }

}
