package org.wildhamsters.battleships;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

/**
 * @author Dominik Żebracki
 */
class Users {

    private final Map<String, UserDetails> users;
    private final PasswordEncoder passwordEncoder;

    Users(Map<String, UserDetails> users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        System.err.println(users);
    }

    void save(UserDto userDto) {
        users.put(userDto.name(), User.withUsername(userDto.name())
                .password(passwordEncoder.encode(userDto.password()))
                .authorities("USER")
                .build());
        System.err.println(users);
    }

    UserDetails findByUsername(String username) {
        System.out.println(users);
        return users.get(username);
    }
}
