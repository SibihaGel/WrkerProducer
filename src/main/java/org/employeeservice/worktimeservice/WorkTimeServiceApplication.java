package org.employeeservice.worktimeservice;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.employeeservice.worktimeservice.DAO.EmployeeRepository;
import org.employeeservice.worktimeservice.kafka.ProducerApi;
import org.employeeservice.worktimeservice.model.Employee;
import org.employeeservice.worktimeservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@SpringBootApplication
public class WorkTimeServiceApplication {






    public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(WorkTimeServiceApplication.class, args);

	RobotService robotService = context.getBean(RobotService.class);
			robotService.doPrintRobot();
			robotService.doPrintRobot1FromMap();
			robotService.doPrintRobot2FromMap();
		//	context.close();






		// Получаем экземпляр ProducerService из контекста приложения
		ProducerApi producerApi = context.getBean(ProducerApi.class);
		//getBean(ProducerApi.class);

		// Вызываем метод generateEmployeeAndSendToKafka
		producerApi.generateEmployeeAndSendToKafka();
	}

	@Bean
	CommandLineRunner runner (ProducerApi producer) {
		return args -> {
			Employee employee = new Employee();
		};
	}


	}



