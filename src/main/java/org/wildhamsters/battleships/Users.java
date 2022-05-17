package org.wildhamsters.battleships;

/**
 * @author Dominik Żebracki
 */
interface Users {
    void save(UserDto userDto) throws AccountExistException;

    User findByUsername(String username);
}
