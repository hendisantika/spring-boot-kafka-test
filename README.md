# Spring Boot Kafka test example

Spring Kafka example with JUnit 5 using EmbeddedKafka/`spring-kafka-test` and also
using [Testcontainers](https://www.testcontainers.org/).

## How to run

First start the docker-compose which contains ZooKeeper, Kafka, Kafdrop, and MySQL.

```bash
$ docker-compose -f docker-compose.yml up -d
```

Once all the containers are healthy start the application,

```bash
$ ./mvnw clean spring-boot:run
```

Open the browser `localhost:8080/apidocs`.

You can interact with the `random` API to create a random user which then will be sent to Kafka and consumed by the
consumer (
see [`consumer/UserKafkaListener.java`](https://github.com/kasramp/spring-kafka-test/blob/master/src/main/java/com/madadipouya/springkafkatest/consumer/UserKafkaListener.java)
file) and finally saves into the database.

To see whether the message has been sent to Kafka, open your
browser `http://localhost:8080/topic/com.hendisantika.springbootkafkatest.use` (Kafdrop environment),
you should be able to see all messages that sent to `kafka.user` topic.

To run Flyway migration scripts only run,

```bash
$ ./mvnw clean flyway:migrate
```
