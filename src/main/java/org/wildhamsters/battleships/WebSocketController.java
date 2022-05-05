package org.wildhamsters.battleships;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * @author Mariusz Bal
 */
@Controller
class WebSocketController {
    private final GameService gameService;

    WebSocketController(GameService gameService) {
        this.gameService = gameService;
    }

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/room")
    public void sendSpecific(@Payload Message<String> msg, Principal user,
                             @Header("simpSessionId") String sessionId) throws JsonProcessingException {
        ConnectionStatus connectionStatus = gameService.processConnectingPlayers(new ConnectedPlayer(user.getName(), sessionId));

        String resultJSON = new ObjectMapper().writeValueAsString(connectionStatus);
        simpMessagingTemplate.convertAndSendToUser(sessionId,
                "/queue/specific-user", resultJSON);
        simpMessagingTemplate.convertAndSendToUser(connectionStatus.playerOneSessionId(),
                "/queue/specific-user", resultJSON);
    }

    @MessageMapping("/gameplay")
    public void sendGameplay(String json, Principal user,
                             @Header("simpSessionId") String sessionId) throws JsonProcessingException {
        GameplayUserShotData data = new ObjectMapper().readValue(json, GameplayUserShotData.class);
        Result result = gameService.shoot(data.roomId(), data.cell());

        String resultJSON = new ObjectMapper().writeValueAsString(result);
        simpMessagingTemplate.convertAndSendToUser(result.currentTurnPlayer(),
                "/queue/specific-user", resultJSON);
        simpMessagingTemplate.convertAndSendToUser(result.opponent(),
                "/queue/specific-user", resultJSON);
    }
}
