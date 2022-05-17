package org.wildhamsters.battleships.play;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class Stats implements MatchStatisticsRepository{
    @Override
    public MatchStatisticsEntity save(MatchStatisticsEntity entity) {
        return null;
    }

    @Override
    public <S extends MatchStatisticsEntity> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<MatchStatisticsEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<MatchStatisticsEntity> findAll() {
        return null;
    }

    @Override
    public Iterable<MatchStatisticsEntity> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(MatchStatisticsEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends MatchStatisticsEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
