package org.wildhamsters.battleships;

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
        final UserDetails retrievedUser = users.findByUsername(username);
        if(retrievedUser == null) {
            throw new UsernameNotFoundException("User %s not found".formatted(username));
        }
        return retrievedUser;
    }

}
