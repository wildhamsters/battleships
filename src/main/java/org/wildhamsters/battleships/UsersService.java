package org.wildhamsters.battleships;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Dominik Żebracki
 */
class UsersService implements UserDetailsService {

    private final Users users;

    UsersService(Users users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserEntity loadedUser = users.findByUsername(username);
        if(loadedUser == null) {
            throw new UsernameNotFoundException("User %s not found".formatted(username));
        }
        return User.withUsername(loadedUser.name())
                .password(loadedUser.password())
                .authorities(loadedUser.authority())
                .accountExpired(loadedUser.accountExpired())
                .accountLocked(loadedUser.accountLocked())
                .credentialsExpired(loadedUser.credentialsExpired())
                .disabled(loadedUser.disabled())
                .build();
    }
}
