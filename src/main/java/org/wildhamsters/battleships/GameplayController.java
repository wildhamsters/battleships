package org.wildhamsters.battleships;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wildhamsters.battleships.board.FieldState;

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
            FieldState updatedState = gameService.verifyShot(cell);
            Boolean finished = gameService.isRoundFinished();
            return ResponseEntity.ok(new Pair<>(updatedState, finished).toString());
        } catch (IllegalShotException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/setup")
    ResponseEntity<List<Integer>> placeShip(@RequestBody List<Integer> cells) {
        return ResponseEntity.ok().body(cells);
    }

}
