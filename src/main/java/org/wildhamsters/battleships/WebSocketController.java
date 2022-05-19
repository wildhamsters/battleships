package org.wildhamsters.battleships;

import java.security.Principal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * @author Mariusz Bal
 */
@Controller
class WebSocketController {
        private final GameService gameService;
        private final RabbitTemplate rabbitTemplate;

        WebSocketController(GameService gameService, RabbitTemplate rabbitTemplate) {
                this.gameService = gameService;
                this.rabbitTemplate = rabbitTemplate;
        }

        @Autowired
        private SimpMessagingTemplate simpMessagingTemplate;

        @MessageMapping("/room")
        public void sendSpecific(@Payload Message<String> msg, Principal user,
                        @Header("simpSessionId") String sessionId) throws JsonProcessingException {

                ConnectionStatus connectionStatus;
                gameService.addPlayer(new ConnectedPlayer(user.getName(), sessionId));
                if(gameService.areTwoPlayersConnected()) {
                        rabbitTemplate.convertAndSend("message_exchange", "requestShips_key",
                                new RequestForShips("message from WebSocketController"));
                        connectionStatus = gameService.getConnectionStatus();
                } else {
                        connectionStatus = gameService.createPlayerWaitingForOpponentStatus();
                }
                        String resultJSON = new ObjectMapper().writeValueAsString(connectionStatus);
                        simpMessagingTemplate.convertAndSendToUser(connectionStatus.playerOneSessionId(),
                                "/queue/specific-user", resultJSON);
                        if (connectionStatus.playerTwoSessionId() != null)
                                simpMessagingTemplate.convertAndSendToUser(connectionStatus.playerTwoSessionId(),
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

        @MessageMapping("/gameplay/surrender")
        public void giveUp(String json, Principal user,
                                 @Header("simpSessionId") String sessionId) throws JsonProcessingException {
                GameplayUserShotData data = new ObjectMapper().readValue(json, GameplayUserShotData.class);
                SurrenderResult result = gameService.surrender(data.roomId(), sessionId);

                String resultJSON = new ObjectMapper().writeValueAsString(result);
                simpMessagingTemplate.convertAndSendToUser(result.surrenderPlayerSessionId(),
                        "/queue/specific-user", resultJSON);
                simpMessagingTemplate.convertAndSendToUser(result.winPlayerSessionId(),
                        "/queue/specific-user", resultJSON);
        }
}
