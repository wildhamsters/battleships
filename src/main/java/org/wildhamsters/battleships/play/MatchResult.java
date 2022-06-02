package org.wildhamsters.battleships.play;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * @author Piotr Chowaniec
 */
@SuppressFBWarnings(
        value = "URF_UNREAD_FIELD",
        justification = "Fields are used by the front-end"
)
class MatchResult {
    private final String gameId;
    private final Player playerOne;
    private final Player playerTwo;
    private Player winner;

    MatchResult(String gameId, Player playerOne, Player playerTwo) {
        this.gameId = gameId;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
