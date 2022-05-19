package org.wildhamsters.battleships;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Mariusz Bal
 */
@Controller
class RegistrationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    RegistrationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/registration")
    ResponseEntity<String> register(@RequestParam Map<String, String> map, HttpServletRequest request) {
        try {
            userService.registerUser(new UserDto(map.get("username"), passwordEncoder.encode(map.get("password")), map.get("email")));
            request.login(map.get("username"), map.get("password"));
            return ResponseEntity.status(HttpStatus.CREATED).body("/welcome");
        } catch (AccountExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User account could not be created");
        } catch (ServletException e) {
            return ResponseEntity.status(HttpStatus.CREATED).body("/login");
        }
    }
}
