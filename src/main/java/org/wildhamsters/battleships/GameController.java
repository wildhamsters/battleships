package org.wildhamsters.battleships;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Mariusz Bal
 */
@Controller
class GameController {

    @GetMapping
    String hello() {
        return "register.html";
    }

    @GetMapping("/register")
    String showRegistrationPage() {
        return "register.html";
    }

    @GetMapping("/index")
    String showPage() {
        return "index.html";
    }

    @GetMapping("/welcome")
    String showWelcomePage() {
        return "welcome.html";
    }

    @GetMapping("/stats")
    String showStatisticsPage() {
        return "stats.html";
    }
}
