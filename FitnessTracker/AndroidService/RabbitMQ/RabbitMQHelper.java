package com.example.myapplication.RabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQHelper {

    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;

    public RabbitMQHelper(String uri) throws Exception {
        factory = new ConnectionFactory();
        factory.setUri(uri);
        connect();
    }

    private void connect() throws Exception {
        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public void declareQueue(String queueName) throws Exception {
        channel.queueDeclare(queueName, false, false, false, null);
    }

    public void publishMessage(String queueName, String message) throws Exception {
        channel.basicPublish("", queueName, null, message.getBytes());
    }

    public void close() throws Exception {
        channel.close();
        connection.close();
    }

    public Channel getChannel() {
        return channel;
    }
}
