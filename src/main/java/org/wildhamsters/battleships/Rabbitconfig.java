package org.wildhamsters.battleships;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Rabbitconfig {

    static final String QUEUE = "requestShips_queue";
    static final String EXCHANGE = "message_exchange";
    static final String ROUTING_KEY = "requestShips_key";
    static final String RESPONSE_QUEUE = "shipPositions_queue";
    static final String POSITIONS_KEY = "shipPositions_key";

    @Bean
    @Qualifier("requestShips_key")
    Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    Binding binding(@Qualifier("requestShips_key")Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }

    @Bean
    @Qualifier("shipPositions")
    Queue queue2() {
        return new Queue(RESPONSE_QUEUE);
    }

    @Bean
    Binding binding2(@Qualifier("shipPositions")Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(POSITIONS_KEY);
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
