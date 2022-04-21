package org.wildhamsters.battleships;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Mariusz Bal
 */
@Controller
class GameStartController {

    @GetMapping
    String hello() {
        return "index.html";
    }
}
