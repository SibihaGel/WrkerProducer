package org.employeeservice.worktimeservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.employeeservice.worktimeservice.model.Employee;
import org.employeeservice.worktimeservice.service.EmployeeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.UUID;

@Service
public class Producer {

    private final EmployeeRequest employeeRequest;

    public Producer(EmployeeRequest employeeRequest) {
        this.employeeRequest = employeeRequest;
    }


    public static void main(String[] args) {

        final String BOOTSTRAP_SERVER = "127.0.0.1:9092";
        final String TOPIC_NAME = "demo-topic";
        final int MESSAGES_NUMBER = 1000;

        // Initialize Logger
        Logger logger = LoggerFactory.getLogger(Producer.class);

        // Create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // Generate messages with Employee data
        for (int i = 0; i < MESSAGES_NUMBER; i++) {
            Employee employee = generateEmployee(); // Генерация Employee

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            String employeeJson;
            try {
                employeeJson = objectMapper.writeValueAsString(employee);
                logger.info("Sending Employee to Kafka: " + employeeJson); // вывод лога о сотруднике
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return;
            }

            ProducerRecord<String, String> record = new ProducerRecord<>(
                    TOPIC_NAME,
                    "key_" + i % 3,
                    employeeJson
            );

            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    logger.info("received new metadata, topic: " + metadata.topic()
                            + " partition: " + metadata.partition()
                            + " offsets: " + metadata.offset()
                            + " timestamp: " + metadata.timestamp());
                } else {
                    logger.error("error producing: ", exception);
                }
            });

            // Observing Kafka round-robin feature
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Flush data and close producer
        producer.flush();
        producer.close();
    }

    // Метод для генерации случайного Employee
    private static Employee generateEmployee() {
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID().toString());
        employee.setLoginTime(LocalDateTime.now());
        employee.setFirstName("first");
        employee.setLastName("second");
        employee.setSurname("three");


        return employee;
    }
}

