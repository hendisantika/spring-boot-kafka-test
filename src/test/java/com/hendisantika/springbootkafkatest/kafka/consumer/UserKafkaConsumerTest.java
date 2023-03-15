package com.hendisantika.springbootkafkatest.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hendisantika.springbootkafkatest.entity.User;
import com.hendisantika.springbootkafkatest.service.UserService;
import org.apache.kafka.clients.producer.Producer;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-kafka-test
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/15/23
 * Time: 09:08
 * To change this template use File | Settings | File Templates.
 */
@EmbeddedKafka
@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserKafkaConsumerTest {

    private final String TOPIC_NAME = "com.madadipouya.kafka.user";
    @Captor
    ArgumentCaptor<User> userArgumentCaptor;
    @Captor
    ArgumentCaptor<String> topicArgumentCaptor;
    @Captor
    ArgumentCaptor<Integer> partitionArgumentCaptor;
    @Captor
    ArgumentCaptor<Long> offsetArgumentCaptor;
    private Producer<String, String> producer;
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;
    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private UserKafkaConsumer userKafkaConsumer;
    @MockBean
    private UserService userService;

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:h2:mem:test");
        registry.add("spring.datasource.driverClassName", () -> "org.h2.Driver");
        registry.add("spring.datasource.username", () -> "root");
        registry.add("spring.datasource.password", () -> "secret");
        registry.add("spring.flyway.enabled", () -> "false");
    }

}
