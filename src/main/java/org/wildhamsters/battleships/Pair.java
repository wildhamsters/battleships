package org.wildhamsters.battleships;

/**
 * @author Piotr Chowaniec
 */
class Pair <U, T> {

    private final U u;
    private final T t;

    Pair(U u, T t) {
        this.u = u;
        this.t = t;
    }

    U getU() {
        return u;
    }

    T getT() {
        return t;
    }

    @Override
    public String toString() {
        return "{\"u\":\"" + u + "\", \"t\":" + t + "}";
    }
}
