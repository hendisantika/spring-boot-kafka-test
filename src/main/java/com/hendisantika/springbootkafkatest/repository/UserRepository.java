package com.hendisantika.springbootkafkatest.repository;

import com.hendisantika.springbootkafkatest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-kafka-test
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/14/23
 * Time: 07:30
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<User> getByFirstNameIgnoreCaseOrderByFirstNameAscLastNameAsc(String firstName);
}
