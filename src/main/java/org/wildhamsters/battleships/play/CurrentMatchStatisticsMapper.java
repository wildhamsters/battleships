package org.wildhamsters.battleships.play;

/**
 * @author Piotr Chowaniec
 */
public class CurrentMatchStatisticsMapper implements Mapper<MatchStatistics, CurrentMatchStatistics> {

    @Override
    public CurrentMatchStatistics map(MatchStatistics key) {
        return new CurrentMatchStatistics(
                key.getMatchId(),
                key.getStartTime(),
                key.getAccurateShots(),
                key.getMissedShots(),
                key.getRounds(),
                key.getFinishTime()
        );
    }
}
