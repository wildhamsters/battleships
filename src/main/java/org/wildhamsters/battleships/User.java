package org.wildhamsters.battleships;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Dominik Żebracki
 */
record User(int id,
            String name,
            String email,
            String password,
            GrantedAuthority authority,
            boolean accountExpired,
            boolean accountLocked,
            boolean credentialsExpired,
            boolean disabled) {
}