package org.example;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) throws IOException, TimeoutException {

        //CrateCSV////////////////////////////////////////////////////////
        String filename = "Measurements_" + System.currentTimeMillis() + ".csv";
        File file = new File(filename);
        FileWriter fileWriter;

        try {
            fileWriter = new FileWriter(file, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        try {
            // Write accelerometer data to CSV file
            bufferedWriter.write("X_accelerom, Y_accelerom, Z_accelerom, Timestamp, Exercise_Type\n");
            System.out.println("CSV file created.");

            bufferedWriter.flush();
        } catch (IOException e) {
          System.out.println(e);
        }


        //RabbitMQ////////////////////////////////////////////////////////
        ConnectionFactory factory = new ConnectionFactory();

        String uri = "amqps://gwqwxywo:ntgFhoV5MGfdg-drZjLtPBHYRnSJvx1C@goose.rmq2.cloudamqp.com/gwqwxywo";
        try {
            factory.setAutomaticRecoveryEnabled(false);
            factory.setUri(uri);
        } catch (KeyManagementException | NoSuchAlgorithmException | URISyntaxException e1) {
            e1.printStackTrace();

        }

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.confirmSelect();

        channel.queueDeclare("queue", false, false, false, null);

        System.out.println("Waiting for messages from queue");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try {
                // Write the received message to the CSV file
                bufferedWriter.write(message);
                // Flush the buffer to ensure the message is written immediately
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        channel.basicConsume("queue", true, deliverCallback, consumerTag -> { });
    }
}