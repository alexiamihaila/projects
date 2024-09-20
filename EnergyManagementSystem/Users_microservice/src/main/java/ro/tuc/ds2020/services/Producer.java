package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.UserController;
import ro.tuc.ds2020.dtos.UserRabbitDTO;

@Component
@Service
public class Producer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.json3.key}")
    private String routingJsonKey3;

    public Producer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessageToQueue (UserRabbitDTO userRabbitDTO){
        LOGGER.info(String.format("Sent user id to queue -> %s", userRabbitDTO.toString()));
        rabbitTemplate.convertAndSend(exchange, routingJsonKey3, userRabbitDTO);
    }
}
