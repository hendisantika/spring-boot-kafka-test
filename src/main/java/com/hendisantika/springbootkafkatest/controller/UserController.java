package com.hendisantika.springbootkafkatest.controller;

import com.github.javafaker.Faker;
import com.hendisantika.springbootkafkatest.dto.UserDto;
import com.hendisantika.springbootkafkatest.entity.User;
import com.hendisantika.springbootkafkatest.kafka.producer.UserKafkaProducer;
import com.hendisantika.springbootkafkatest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-kafka-test
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/14/23
 * Time: 07:33
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/v1/users")
@Tag(name = "User", description = "User APIs")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserKafkaProducer kafkaProducer;

    private final UserService userService;

    @GetMapping("/random")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Create a user", description = "Creates a random user and write it to Kafka which is consumed by the listener")
    public void generateRandomUser() {
        Faker faker = new Faker();
        kafkaProducer.writeToKafka(new UserDto(UUID.randomUUID().toString(), faker.name().firstName(), faker.name().lastName()));
    }

    @GetMapping("/{firstName}")
    @ResponseStatus
    @Operation(summary = "Create a user", description = "Returns a list of users that matchers the given name")
    public List<UserDto> getUsers(@PathVariable(name = "firstName") String name) {
        List<User> users = userService.getUsers(name);
        return users.stream().map(user -> new UserDto(user.getId(), user.getFirstName(), user.getLastName())).collect(Collectors.toList());
    }
}
