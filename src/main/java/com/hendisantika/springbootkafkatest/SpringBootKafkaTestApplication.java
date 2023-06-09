package com.hendisantika.springbootkafkatest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hendisantika.springbootkafkatest")
public class SpringBootKafkaTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootKafkaTestApplication.class, args);
    }

}
