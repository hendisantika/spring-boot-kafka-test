package com.hendisantika.springbootkafkatest.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hendisantika.springbootkafkatest.dto.UserDto;
import com.hendisantika.springbootkafkatest.entity.User;
import com.hendisantika.springbootkafkatest.service.UserService;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

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
@EmbeddedKafka(partitions = 1, brokerProperties = "listeners=PLAINTEXT://localhost:9092")
@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserKafkaConsumerTest {

    private final String TOPIC_NAME = "com.hendisantika.springbootkafkatest.dto.UserDto";
    @Captor
    ArgumentCaptor<UserDto> userArgumentCaptor;
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

    @BeforeAll
    void setUp() {
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        producer =
                new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), new StringSerializer()).createProducer();
    }

    @Test
    void testLogKafkaMessages() throws JsonProcessingException {
        // Write a message (John Wick user) to Kafka using a test producer
        String uuid = "11111";
        String message = objectMapper.writeValueAsString(new User(uuid, "John", "Wick"));
        producer.send(new ProducerRecord<>(TOPIC_NAME, 0, uuid, message));
        producer.flush();

        // Read the message and assert its properties
        verify(userKafkaConsumer, timeout(5000).times(1))
                .logKafkaMessages(userArgumentCaptor.capture(), topicArgumentCaptor.capture(),
                        partitionArgumentCaptor.capture(), offsetArgumentCaptor.capture());

        UserDto user = userArgumentCaptor.getValue();
        assertNotNull(user);
        assertEquals("11111", user.getUuid());
        assertEquals("John", user.getFirstName());
        assertEquals("Wick", user.getLastName());
        assertEquals(TOPIC_NAME, topicArgumentCaptor.getValue());
        assertEquals(0, partitionArgumentCaptor.getValue());
        assertEquals(0, offsetArgumentCaptor.getValue());
        verify(userService).save(any(UserDto.class));
    }

    @AfterAll
    void shutdown() {
        producer.close();
        embeddedKafkaBroker.destroy();
    }
}
