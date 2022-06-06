package org.wildhamsters.battleships.configuration;

import org.wildhamsters.battleships.ExcludeFromJacocoGeneratedReport;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.wildhamsters.battleships.board.Board;
import org.wildhamsters.battleships.fleet.Fleet;

import java.util.ArrayList;
import java.util.List;

/**
 * Game settings needed to start a game and creating
 * {@link org.wildhamsters.battleships.play.GameRoom}.
 *
 * @author Dominik Żebracki
 */
//@SuppressFBWarnings(
//        value = "EI_EXPOSE_REP",
//        justification = "Can't fix that for now"
//)
public class GameSettings {
    private final List<PlayerSettings> playerSettings;

    public GameSettings(List<PlayerSettings> playerSettings) {
        if (playerSettings == null) {
            this.playerSettings = new ArrayList<>();
        } else {
            this.playerSettings = new ArrayList<>(playerSettings);
        }
    }

    public PlayerSettings getFirstPlayerSettings() {
        return this.playerSettings.get(0);
    }

    public PlayerSettings getSecondPlayerSettings() {
        return this.playerSettings.get(1);
    }

    public Fleet firstPlayersFleet() {
        return playerSettings.get(0).getFleet();
    }

    public Fleet secondPlayersFleet() {
        return playerSettings.get(1).getFleet();
    }

    /**
     * Individual player data.
     */

    @ExcludeFromJacocoGeneratedReport
    public static class PlayerSettings {
        String id;
        String name;
        Board board;
        Fleet fleet;

        public PlayerSettings(String id, String name, Board board, Fleet fleet) {
            this.id = id;
            this.name = name;
            if (board == null) {
                this.board = Board.create();
            } else {
                this.board = Board.create(board);
            }
            if (fleet == null) {
                this.fleet = new Fleet();
            } else {
                this.fleet = new Fleet(fleet);
            }
        }

        public Fleet getFleet() {
            return new Fleet(this.fleet);
        }

        public String getName() {
            return this.name;
        }

        public String getId() {
            return this.id;
        }

        public Board getBoard() {
            return Board.create(this.board);
        }
    }
}
