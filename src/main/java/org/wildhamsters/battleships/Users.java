package org.wildhamsters.battleships;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

/**
 * @author Dominik Żebracki
 */
interface Users {
    void save(UserDto userDto) throws AccountExistException;

    UserEntity findByUsername(String username);
}
