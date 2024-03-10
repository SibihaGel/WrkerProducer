package org.employeeservice.worktimeservice.kafka;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

    @Configuration
    public class KafkaConfig {

   /*     @Value("localhost:9092")
        private String bootstrapServers;

        @Bean
        public KafkaProducer<String, String> kafkaProducer() {
            Properties properties = new Properties();
            properties.setProperty("bootstrap.servers", bootstrapServers);
            properties.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            properties.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

            return new KafkaProducer<>(properties);
        }*/
}

