package org.wildhamsters.battleships;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Mariusz Bal
 */
@Controller
class RegistrationController {

    @PostMapping("/registration")
    void register(@RequestParam Map<String, String> map, HttpServletResponse response) throws IOException {
        var name = map.get("username");
        var email = map.get("email");
        var password = map.get("password");
        System.out.println(new User(name, email, password));
        response.sendRedirect("/login");
    }

    @GetMapping("/register")
    String showRegistrationPage() {
        return "register.html";
    }
}
