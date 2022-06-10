package org.wildhamsters.battleships.play;

import java.time.LocalDateTime;

/**
 * @author Piotr Chowaniec
 */
public record CurrentMatchStatistics(String matchId,
                              LocalDateTime startTime,
                              int accurateShots,
                              int missedShots,
                              int rounds,
                              LocalDateTime finishTime) {
}
