package org.wildhamsters.battleships.configuration;

import org.wildhamsters.battleships.ExcludeFromJacocoGeneratedReport;
import org.wildhamsters.battleships.fleet.ShipPosition;

import java.util.List;
@ExcludeFromJacocoGeneratedReport
public record PositionsDTO(List<ShipPosition> positions) {
}
