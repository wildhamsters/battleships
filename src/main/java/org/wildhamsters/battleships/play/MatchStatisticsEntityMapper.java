package org.wildhamsters.battleships.play;

/**
 * Maps MatchStatistic to entity for database repository.
 *
 * @author Piotr Chowaniec
 */
public class MatchStatisticsEntityMapper implements Mapper<MatchStatistics, MatchStatisticsEntity> {

    @Override
    public MatchStatisticsEntity map(MatchStatistics key) {
        MatchStatisticsEntity entity = new MatchStatisticsEntity();
        entity.setMatchId(key.getMatchId());
        entity.setAccurateShots(key.getAccurateShots());
        entity.setMissedShots(key.getMissedShots());
        entity.setRounds(key.getRounds());
        entity.setStartTime(key.getStartTime());
        entity.setFinishTime(key.getFinishTime());
        return entity;
    }
}
