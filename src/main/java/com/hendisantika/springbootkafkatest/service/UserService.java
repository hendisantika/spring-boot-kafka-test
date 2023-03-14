package com.hendisantika.springbootkafkatest.service;

import com.hendisantika.springbootkafkatest.dto.UserDto;
import com.hendisantika.springbootkafkatest.entity.User;
import com.hendisantika.springbootkafkatest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-kafka-test
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/14/23
 * Time: 07:31
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public void save(UserDto user) {
        log.info("Saving user with id = {}", user.getUuid());
        userRepository.save(new User(user.getUuid(), user.getFirstName(), user.getLastName()));
    }
}
