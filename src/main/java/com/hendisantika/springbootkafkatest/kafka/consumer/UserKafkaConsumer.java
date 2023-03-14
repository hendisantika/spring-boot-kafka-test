package com.hendisantika.springbootkafkatest.kafka.consumer;

import com.hendisantika.springbootkafkatest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-kafka-test
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/14/23
 * Time: 07:34
 * To change this template use File | Settings | File Templates.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class UserKafkaConsumer {

    private final UserService userService;
}
