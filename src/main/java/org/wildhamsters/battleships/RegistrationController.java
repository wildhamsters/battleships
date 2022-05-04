package org.wildhamsters.battleships;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Mariusz Bal
 */
@Controller
class RegistrationController {

    private final Users users;

    RegistrationController(Users users) {
        this.users = users;
    }

    @PostMapping("/registration")
    void register(@RequestParam Map<String, String> map, HttpServletResponse response) throws IOException {
        users.save(new UserDto(map.get("username"), map.get("password"), map.get("email")));
        response.sendRedirect("/login");
    }

    @GetMapping("/register")
    String showRegistrationPage() {
        return "register.html";
    }
}
