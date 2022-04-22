package org.wildhamsters.battleships;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * @author Piotr Chowaniec
 */
@Service
class GameService {

    //TODO  Add Fleet, update fleet, check if Player won.

    private final ShootVerifier shootVerifier;
    private final Board board;

    GameService() {
        ArrayList<FieldState> list = new ArrayList<>();
        IntStream.range(0, 25).forEach(x -> list.add(FieldState.WATER));
        board = new DefaultBoard(list);
        shootVerifier = new ShootVerifier(board.size());
    }

    FieldState verifyShoot(int position) throws IllegalShootException {
        FieldState state = shootVerifier.verifyShoot(position, board);
        updateFieldState(state, position);
        return state;
    }

    private void updateFieldState(FieldState newState, int position) {
        board.setField(newState, position);
    }
}
