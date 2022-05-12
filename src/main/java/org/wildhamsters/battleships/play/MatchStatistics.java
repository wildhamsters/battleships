package org.wildhamsters.battleships.play;

import org.wildhamsters.battleships.board.FieldState;

import java.time.LocalDateTime;

/**
 * @author Piotr Chowaniec
 */
class MatchStatistics {

    private final String matchId;
    private int accurateShots = 0;
    private int missedShots = 0;
    private int rounds = 0;
    private final LocalDateTime startTime;
    private LocalDateTime finishTime;

    MatchStatistics(String matchId) {
        this.matchId = matchId;
        startTime = LocalDateTime.now();
    }

    void updateShots(FieldState fieldState) {
        switch (fieldState) {
            case ACCURATE_SHOT -> accurateShots++;
            case MISSED_SHOT -> missedShots++;
        }
    }

    void updateRound(FieldState fieldState) {
        if (fieldState == FieldState.MISSED_SHOT) rounds++;
    }

    void setFinishTime() {
        finishTime = LocalDateTime.now();
    }

    String getMatchId() {
        return matchId;
    }

    int getAccurateShots() {
        return accurateShots;
    }

    int getMissedShots() {
        return missedShots;
    }

    int getRounds() {
        return rounds;
    }

    LocalDateTime getStartTime() {
        return startTime;
    }

    LocalDateTime getFinishTime() {
        return finishTime;
    }
}
