package org.wildhamsters.battleships;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Dominik Żebracki
 */
record UserEntity(int id,
                  String name,
                  String email,
                  String password,
                  GrantedAuthority authority,
                  boolean accountExpired,
                  boolean accountLocked,
                  boolean credentialsExpired,
                  boolean disabled) {
}