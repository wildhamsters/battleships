package org.wildhamsters.battleships;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mariusz Bal
 */
@RestController
class GameplayController {

    @GetMapping("/shoot/{cell}")
    ResponseEntity<List<Integer>> shoot(@PathVariable int cell) {
        if (cell < 0)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(List.of(1, 2, 3, 4, 5, 6, 7, 9));
    }

    @PostMapping("/setup")
    ResponseEntity<List<Integer>> placeShip(@RequestBody List<Integer> cells) {
        return ResponseEntity.ok().body(cells);
    }

}
