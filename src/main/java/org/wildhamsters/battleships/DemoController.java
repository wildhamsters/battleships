package org.wildhamsters.battleships;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wildhamsters.battleships.board.FieldState;

/**
 * @author Dominik Żebracki
 */
@RestController
class DemoController {

    private final GameService gameService;

    DemoController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/clear")
    ResponseEntity<FieldState> clear() {
        gameService.resetGameStatus();
        return ResponseEntity.ok().build();
    }
}
