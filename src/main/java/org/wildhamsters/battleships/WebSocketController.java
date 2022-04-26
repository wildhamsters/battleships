package org.wildhamsters.battleships;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @author Mariusz Bal
 */
@Controller
class WebSocketController {
    private final GameService gameService;

    WebSocketController(GameService gameService){
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
}
