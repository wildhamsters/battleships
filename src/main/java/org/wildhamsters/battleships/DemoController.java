package org.wildhamsters.battleships;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dominik Żebracki
 */
@RestController
class DemoController {

    private final Board board;

    DemoController(Board board) {
        this.board = board;
    }

    @GetMapping("/clear")
    ResponseEntity<FieldState> clear() {
        board.clearBoard();
        return ResponseEntity.ok().build();
    }
}
