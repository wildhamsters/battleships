package org.wildhamsters.battleships;

/**
 * @author Mariusz Bal
 */
record SurrenderResult(Event event,
                       String surrenderPlayerSessionId,
                       String winPlayerSessionId,
                       String surrenderMessage,
                       String winnerMessage) {}
