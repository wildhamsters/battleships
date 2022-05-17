package org.wildhamsters.battleships;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Dominik Żebracki
 */
class InMemoryUsers implements Users {

    private final ConcurrentHashMap<String, User> users;
    private final PasswordEncoder passwordEncoder;
    private int usersCount;

    InMemoryUsers(ConcurrentHashMap<String, User> users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        users.put("dominik", new User(1, "dominik", "dom@dom.com", passwordEncoder.encode("1234"),
                new SimpleGrantedAuthority("USER"), false, false, false, false));
        usersCount = 1;
    }

    @Override
    public void save(UserDto userDto) throws AccountExistException {
        if(findByUsername(userDto.name()) != null) {
            throw new AccountExistException("An account for username %s already exist.".formatted(userDto.name()));
        }
        users.put(userDto.name(),
            new User(++usersCount, userDto.name(), userDto.email(), passwordEncoder.encode(userDto.password()),
                    new SimpleGrantedAuthority("USER"), false, false, false, false));
        System.err.println(users);
    }

    @Override
    public User findByUsername(String username) {
        return users.get(username);
    }
}
