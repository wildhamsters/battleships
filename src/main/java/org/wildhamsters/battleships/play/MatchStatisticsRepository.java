package org.wildhamsters.battleships.play;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Piotr Chowaniec
 */
@EnableJpaRepositories
@Repository
public interface MatchStatisticsRepository extends CrudRepository<MatchStatisticsEntity, Long> {

    MatchStatisticsEntity save(MatchStatisticsEntity entity);
}
