package ro.tuc.ds2020.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);

    @Value("${rabbitmq.queue.json3.name}")
    private String jsonQueue3;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.json3.key}")
    private String routingJsonKey3;


    @Bean
    public TopicExchange exchange() {
        logger.info("Creating TopicExchange: {}", exchange);
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue jsonQueue() {
        logger.info("Creating Queue: {}", jsonQueue3);
        return new Queue("queue3");
    }

    @Bean
    public Binding jsonBinding() {
        logger.info("Creating Binding for Queue: {} to Exchange: {} with Routing Key: {}", jsonQueue3, exchange, routingJsonKey3);
        return BindingBuilder.bind(jsonQueue()).to(exchange()).with(routingJsonKey3);
    }

    @Bean
    public MessageConverter converter() {
        logger.info("Creating Jackson2JsonMessageConverter");
        return new Jackson2JsonMessageConverter();
    }



}
