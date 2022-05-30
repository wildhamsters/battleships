package org.wildhamsters.battleships.configuration;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.wildhamsters.battleships.fleet.ShipPosition;

import java.util.List;

@SuppressFBWarnings(
        value = "EI_EXPOSE_REP",
        justification = "Can't fix that for now"
)
public record PositionsDTO(List<ShipPosition> positions) {
}
