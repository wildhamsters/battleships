package org.wildhamsters.battleships;

import java.time.LocalDateTime;

/**
 * @author Piotr Chowaniec
 */
public record SingleMatchStatistics(Integer id,
                                    String matchId,
                                    int accurateShots,
                                    int missedShots,
                                    int rounds,
                                    LocalDateTime startTime,
                                    LocalDateTime finishTime) {
}
