package org.wildhamsters.battleships;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.wildhamsters.battleships.board.Board;
import org.wildhamsters.battleships.board.FieldState;
import org.wildhamsters.battleships.play.IllegalShotException;

import java.security.Principal;
import java.util.*;

/**
 * @author Mariusz Bal
 */
@Controller
class WebSocketController {
    private final GameService gameService;

    WebSocketController(GameService gameService) {
        this.gameService = gameService;
    }

    @MessageMapping("/action")
    @SendTo("/shots/list")
    String shoot(int cell) throws JsonProcessingException {
        Result result;
        try {
            FieldState updatedState = gameService.verifyShot(cell);
            Boolean finished = gameService.isRoundFinished();
            result = new Result(cell,updatedState, finished, "", "", "");

        } catch (IllegalShotException e) {
            result = new Result(cell, null, null, "Bad request", "", "");
        }
        return new ObjectMapper().writeValueAsString(result);
    }

    // user2user communication test
    private final Queue<Map.Entry<String, String>> users = new ArrayDeque<>();

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    private int turn =0;
    private final List<Map.Entry<String, String>> pair = new ArrayList<>();

    @MessageMapping("/room")
    public void sendSpecific(@Payload Message<String> msg, Principal user,
                             @Header("simpSessionId") String sessionId) {
        if (users.isEmpty()) {
            users.add(new AbstractMap.SimpleEntry<>(sessionId, user.getName()));
            String s = "No opponents for now";
            simpMessagingTemplate.convertAndSendToUser(sessionId,
                    "/queue/specific-user", s);
        } else {
            Map.Entry<String, String> opponentEntry = users.poll();
            String opponent = opponentEntry.getKey();
            pair.add(new AbstractMap.SimpleEntry<>(sessionId, user.getName()));
            pair.add(opponentEntry);
            boards.put(sessionId, new GameService(Board.create()));
            boards.put(opponent, new GameService(Board.create()));
            String s = "You %s the opponent %s. Starts: %s".formatted(sessionId, opponent, user.getName());
            simpMessagingTemplate.convertAndSendToUser(sessionId,
                    "/queue/specific-user", s);
            simpMessagingTemplate.convertAndSendToUser(opponent,
                    "/queue/specific-user", s);
        }
    }

    private final Map<String, GameService> boards = new HashMap<>();
    @MessageMapping("/gameplay")
    public void sendGameplay(int cell, Principal user,
                             @Header("simpSessionId") String sessionId) throws JsonProcessingException {
        if (pair.size()==2) {
            Result result;
            turn++;
            Map.Entry<String, String> opponentEntry = pair.get(turn % 2);
            String opponent = opponentEntry.getKey();
            try {
                GameService service = boards.get(opponent);
                FieldState updatedState = service.verifyShot(cell);
                Boolean finished = service.isRoundFinished();
                result = new Result(cell, updatedState, finished, "", opponent, opponentEntry.getValue());
            } catch (IllegalShotException e) {
                result = new Result(cell, null, null, "Bad request", opponent, opponentEntry.getValue());
            }

            simpMessagingTemplate.convertAndSendToUser(sessionId,
                    "/queue/specific-user", new ObjectMapper().writeValueAsString(result));
            simpMessagingTemplate.convertAndSendToUser(opponent,
                    "/queue/specific-user", new ObjectMapper().writeValueAsString(result));
        }
    }
}
