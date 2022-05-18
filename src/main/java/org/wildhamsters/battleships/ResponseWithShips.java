package org.wildhamsters.battleships;

import org.wildhamsters.battleships.fleet.PositionsDTO;

import java.util.List;

record ResponseWithShips(List<PositionsDTO> positions) {
}
