package org.wildhamsters.battleships.configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dominik Żebracki
 */
class BoardStructure {

    private final int width;
    private final int height;

    BoardStructure(int width, int height) {
        this.width = width;
        this.height = height;
    }

    List<Integer> fieldSurrounding(int position) {
        var surrounding = new ArrayList<Integer>();
        surrounding.add(position);
        surrounding.add(northWest(position));
        surrounding.add(north(position));
        surrounding.add(northEast(position));
        surrounding.add(east(position));
        surrounding.add(southEast(position));
        surrounding.add(south(position));
        surrounding.add(southWest(position));
        surrounding.add(west(position));
        return surrounding;
    }

    int rowIndex(int position) {
        return position / width;
    }

    int rowBegin(int rowIndex) {
        return rowIndex * width;
    }

    int rowEnd(int rowIndex) {
        return rowBegin(rowIndex) + width - 1;
    }

    int size() {
        return width*height;
    }

    int width() {
        return width;
    }

    int height() {
        return height;
    }

    private int northWest(int position) {
        return position - width - 1;
    }

    private int north(int position) {
        return position - width;
    }

    private int northEast(int position) {
        return position - width + 1;
    }

    private int east(int position) {
        return position + 1;
    }

    private int southEast(int position) {
        return position + width + 1;
    }

    private int south(int position) {
        return position + width;
    }

    private int southWest(int position) {
        return position + width - 1;
    }

    private int west(int position) {
        return position - 1;
    }
}
