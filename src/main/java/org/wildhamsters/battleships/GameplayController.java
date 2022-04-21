package org.wildhamsters.battleships;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Mariusz Bal
 */
@RestController
class GameplayController {

    private final ShootVerificator shootVerificator = new ShootVerificator(new BoardDimension(0, 25));
    private final Board board;
    GameplayController(){
        ArrayList<FieldState> list = new ArrayList<>();
        IntStream.range(0, 25).forEach(x -> list.add(FieldState.WATER));
        board = new DefaultBoard(list);
    }

    @GetMapping("/shoot/{cell}")
    ResponseEntity<FieldState> shoot(@PathVariable int cell) {
        try {
            FieldState updatedState = shootVerificator.verificateShoot(cell, board);
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
