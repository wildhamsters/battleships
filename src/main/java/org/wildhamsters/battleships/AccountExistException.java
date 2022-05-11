package org.wildhamsters.battleships;

/**
 * @author Dominik Żebracki
 */
class AccountExistException extends Exception{

    public AccountExistException(String message) {
        super(message);
    }
}
