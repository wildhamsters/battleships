package org.wildhamsters.battleships;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.wildhamsters.battleships.fleet.PositionsDTO;
import org.wildhamsters.battleships.fleet.ShipPosition;

import java.util.Arrays;
import java.util.List;

@Component
class MessageListener {

    @Autowired
    private GameService gameService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @RabbitListener(queues ="shipPositions_queue" )
    void listener(ResponseWithShips response){
        System.err.println(response);

//        var result = Arrays.stream(positions.split(":"))
//                .map(s -> new ShipPosition(Arrays.stream(s.split(","))
//                        .map(i -> Integer.valueOf(i))
//                        .toList()))
//                .toList();
        System.err.println(response);
        gameService.createTwoPlayersConnectedStatus(response.positions());
    }
}
