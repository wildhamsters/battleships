package org.wildhamsters.battleships.configuration;

import java.util.List;

import org.wildhamsters.battleships.ExcludeFromJacocoGeneratedReport;
import org.wildhamsters.battleships.fleet.ShipPosition;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@ExcludeFromJacocoGeneratedReport
@SuppressFBWarnings(value = "EI_EXPOSE_REP", 
                justification = "Can't fix that for now")
public record PositionsDTO(List<ShipPosition> positions) {
}
