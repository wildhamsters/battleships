package org.wildhamsters.battleships;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wildhamsters.battleships.play.MatchStatisticsEntity;

import java.util.List;

/**
 * @author Mariusz Bal
 */
@ExcludeFromJacocoGeneratedReport
@RestController
@RequestMapping("/statistics")
class StatisticsController {

    @Autowired
    private GameService gameService;

    @GetMapping
    ResponseEntity<List<MatchStatisticsEntity>> readAllStatistics() {
        return ResponseEntity.ok(gameService.findAllStatistics());
    }
}
