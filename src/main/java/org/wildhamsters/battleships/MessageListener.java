package org.wildhamsters.battleships;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
class MessageListener {

    private final GameService gameService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    MessageListener(GameService gameService, SimpMessagingTemplate simpMessagingTemplate) {
        this.gameService = gameService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @RabbitListener(queues ="shipPositions_queue" )
    void listener(ResponseWithShips response){
        gameService.createTwoPlayersConnectedStatus(response.positions());
    }
}
