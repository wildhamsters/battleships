package org.wildhamsters.battleships.play;

/**
 * @author Piotr Chowaniec
 */
class MatchResult {

    //TODO  winner's left fleet
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
