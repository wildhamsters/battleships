package org.wildhamsters.battleships;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

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
            FieldState updatedState = gameService.verifyShoot(cell);
            Boolean finished = gameService.isRoundFinished();
            result = new Result(cell, updatedState, finished, "");

        } catch (IllegalShootException e) {
            result = new Result(cell, null, null, "Bad request");
        }
        return new ObjectMapper().writeValueAsString(result);
    }

    // user2user communication test
    Queue<String> users = new ArrayDeque<>();
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/room")
    public void sendSpecific(@Payload Message msg, Principal user,
                             @Header("simpSessionId") String sessionId) {
        System.out.println("user = " + user);
        if (users.isEmpty()) {
            users.add(sessionId);
            String s = "No opponents for now";
            simpMessagingTemplate.convertAndSendToUser(sessionId,
                    "/queue/specific-user", s);
        } else {
            String opponent = users.poll();
            String s = "There is an opponent; You %s, the opponent %s".formatted(sessionId, opponent);
            simpMessagingTemplate.convertAndSendToUser(sessionId,
                    "/queue/specific-user", s);
            simpMessagingTemplate.convertAndSendToUser(opponent,
                    "/queue/specific-user", s);
        }
    }
}
