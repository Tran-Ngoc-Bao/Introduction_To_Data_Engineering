package com.viettel.vdt.kafka;

import org.apache.kafka.clients.producer.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Producer {
    private static final String TOPIC_NAME = "vdt2024";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String dataPath = "../input/log_action.csv";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props);
             BufferedReader reader = new BufferedReader(new FileReader(dataPath))) {
            String line;
            int recordCount = 0;

            while ((line = reader.readLine()) != null) {
                // Process the CSV line and create a ProducerRecord
                String[] fields = line.split(",");
//                String key = fields[0];  // Assuming the key is in the first column
                String key = "Hello";
//                String data = "{ \"student_code\": " + Integer.parseInt(fields[0])
//                        + ", \"activity\": \"" + fields[1]
//                        + "\", \"numberOfFile\": " + Integer.parseInt(fields[2])
//                        + ", \"timestamp\": \"" + fields[3] + "\" }";
                ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, key, line);

                // Send the record and handle the result with callback
                producer.send(record, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        if (exception == null) {
                            System.out.println("Record id " + record.key() + " sent successfully - topic: " + metadata.topic() +
                                    ", partition: " + metadata.partition() +
                                    ", offset: " + metadata.offset());
                        } else {
                            System.err.println("Error while sending record " + record.key() + ": " + exception.getMessage());
                        }
                    }
                });

                recordCount++;
                Thread.sleep(1000);
            }

            System.out.println(recordCount + " records sent to Kafka successfully.");
        } catch (IOException | InterruptedException e) {
            System.out.println("Source not found or Can't connect to Kafka Broker");
        }
    }
}
