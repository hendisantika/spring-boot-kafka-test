package com.hendisantika.springbootkafkatest.service;

import com.hendisantika.springbootkafkatest.dto.UserDto;
import com.hendisantika.springbootkafkatest.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-kafka-test
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/15/23
 * Time: 09:20
 * To change this template use File | Settings | File Templates.
 */
@Testcontainers
@SpringBootTest
public class UserServiceTest {

    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0-debian"));

    @Autowired
    private UserService userService;

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("spring.datasource.url", () -> mySQLContainer.getJdbcUrl());
        registry.add("spring.datasource.driverClassName", () -> mySQLContainer.getDriverClassName());
        registry.add("spring.datasource.username", () -> mySQLContainer.getUsername());
        registry.add("spring.datasource.password", () -> mySQLContainer.getPassword());
        registry.add("spring.flyway.enabled", () -> "true");
    }

    @Test
    void testSaveUser() {
        userService.save(new UserDto(UUID.randomUUID().toString(), "John", "McClane"));
        userService.save(new UserDto(UUID.randomUUID().toString(), "Chandler", "Bing"));
        userService.save(new UserDto(UUID.randomUUID().toString(), "Joey", "Tribbiani"));
        userService.save(new UserDto(UUID.randomUUID().toString(), "John", "Kennedy"));

        List<User> users = userService.getUsers("John");

        assertNotNull(users);
        assertEquals(4, users.size());
        assertEquals("Kennedy", users.get(0).getLastName());
        assertEquals("McClane", users.get(1).getLastName());
        assertEquals("Rambo", users.get(2).getLastName());
        assertEquals("Wick", users.get(3).getLastName());
    }
}
