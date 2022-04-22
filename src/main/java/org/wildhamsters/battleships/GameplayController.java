package org.wildhamsters.battleships;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mariusz Bal
 */
@RestController
class GameplayController {

    private final GameService gameService;

    GameplayController(GameService gameService){
        this.gameService = gameService;
    }

    @GetMapping("/shoot/{cell}")
    ResponseEntity<String> shoot(@PathVariable int cell) {
        try {
            FieldState updatedState = gameService.verifyShoot(cell);
            Boolean finished = gameService.isRoundFinished();
            return ResponseEntity.ok(new Pair<>(updatedState, finished).toString());
        } catch (IllegalShootException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/setup")
    ResponseEntity<List<Integer>> placeShip(@RequestBody List<Integer> cells) {
        return ResponseEntity.ok().body(cells);
    }

}
