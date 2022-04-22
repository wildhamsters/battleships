package org.wildhamsters.battleships;

import org.springframework.http.ResponseEntity;
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
    ResponseEntity<FieldState> shoot(@PathVariable int cell) {
        try {
            FieldState updatedState = gameService.verifyShoot(cell);
            return ResponseEntity.ok(updatedState);
        } catch (IllegalShootException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/setup")
    ResponseEntity<List<Integer>> placeShip(@RequestBody List<Integer> cells) {
        return ResponseEntity.ok().body(cells);
    }

}
