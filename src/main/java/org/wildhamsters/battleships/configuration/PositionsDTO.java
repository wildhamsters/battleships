package org.wildhamsters.battleships.configuration;

import org.wildhamsters.battleships.fleet.ShipPosition;

import java.util.List;

public record PositionsDTO(List<ShipPosition> positions) {
}
