package org.wildhamsters.battleships.fleet;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.List;

/**
 * @author Kevin Nowak
 */

@SuppressFBWarnings(
        value = {"EI_EXPOSE_RE", "EI_EXPOSE_REP2"},
        justification = "Can't fix that for now"
)
public record ShipPosition(List<Integer> positions) {
}
