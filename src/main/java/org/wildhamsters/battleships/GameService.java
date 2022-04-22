package org.wildhamsters.battleships;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Piotr Chowaniec
 */
@Service
public class GameService {

    private final ShootVerificator shootVerificator;
    private final Board board;

    @Autowired
    public GameService(ShootVerificator shootVerificator, Board board) {
        this.shootVerificator = shootVerificator;
        this.board = board;
    }

    public FieldState validateShoot(int position) throws IllegalShootException {
        FieldState state = shootVerificator.verificateShoot(position, board);
        updateFieldState(state, position);
        return state;
    }

    private void updateFieldState(FieldState newState, int position) {
        board.setField(newState, position);
    }
}
