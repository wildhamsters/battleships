package org.wildhamsters.battleships;

interface Board {

    FieldState getFiled(int position);
    void setField(FieldState fieldState, int position);
}
