package org.wildhamsters.battleships.play;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mariusz Bal
 */
public class GameRooms {
    private final List<GameRoom> gameRooms = new ArrayList<>();

    public String addRoom(GameRoom room) {
        gameRooms.add(room);
        return room.obtainUUID();
    }
}
