package org.wildhamsters.battleships;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mariusz Bal
 */
@RestController
class GameStartController {

    @GetMapping
    ResponseEntity<String> hello() {
        return new ResponseEntity<>("Here the game start screen will be shown", HttpStatus.OK);
    }
}
