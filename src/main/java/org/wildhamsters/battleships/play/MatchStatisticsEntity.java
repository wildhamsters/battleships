package org.wildhamsters.battleships.play;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Piotr Chowaniec
 */
@Entity
@Table(name = "match_statistics")
public class MatchStatisticsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "match_id")
    private String matchId;
    @Column(name = "accurate_shots")
    private int accurateShots;
    @Column(name = "missed_shots")
    private int missedShots;
    @Column(name = "rounds")
    private int rounds;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "finish_time")
    private LocalDateTime finishTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public int getAccurateShots() {
        return accurateShots;
    }

    public void setAccurateShots(int accurateShots) {
        this.accurateShots = accurateShots;
    }

    public int getMissedShots() {
        return missedShots;
    }

    public void setMissedShots(int missedShots) {
        this.missedShots = missedShots;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public String toString() {
        return "MathStatisticsDto{" +
                "id=" + id +
                ", matchId='" + matchId + '\'' +
                ", accurateShots=" + accurateShots +
                ", missedShots=" + missedShots +
                ", rounds=" + rounds +
                ", startTime=" + startTime +
                ", finishTime=" + finishTime +
                '}';
    }
}
