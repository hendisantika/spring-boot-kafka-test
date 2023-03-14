package com.hendisantika.springbootkafkatest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-kafka-test
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/14/23
 * Time: 07:27
 * To change this template use File | Settings | File Templates.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder().group("Users").pathsToMatch("/v1/**").build();
    }

    @Bean
    public OpenAPI openApiInfo() {
        return new OpenAPI()
                .info(new Info().title("Spring Kafka Test")
                        .description("An example about Spring Kafka and testing producer and consumer")
                        .version("v0.0.1")
                        .license(new License()));
    }
}
