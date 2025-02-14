package org.employeeservice.worktimeservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.employeeservice.worktimeservice.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Properties;
import java.util.UUID;


/* Создаем новый топик
 * kafka-topics --bootstrap-server localhost:9092 --create --topic new-demo-topic --replication-factor 1 --partitions 4

* выводим список топиков
*
* kafka-topics --bootstrap-server localhost:9092 --list
 *
 * выводи инфо о партициях
 *
 * kafka-topics --bootstrap-server localhost:9092 --describe --topic demo-topic

 *  */


@Service
@AllArgsConstructor
public class ProducerApi {

    private final LinkedList<Employee> employeesCreationEvents;
    final String BOOTSTRAP_SERVER = "127.0.0.1:9092";
    final String TOPIC_NAME = "new-demo-topic";


    final int NUM_PARTITIONS = 4;

    final int MESSAGES_NUMBER = 1000;


  //  @Scheduled(fixedRate = 2500)
    public void generateEmployeeAndSendToKafka() {

        Logger logger = LoggerFactory.getLogger(Producer.class);

        // Create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty("num.partitions", String.valueOf(NUM_PARTITIONS));


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
                Thread.sleep(10000);
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
        employee.setLogoutTime(LocalDateTime.of(2024, 1, 1, 20, 0));
        employee.setLoginTime(LocalDateTime.now().plusHours(2));


        return employee;
    }
}

