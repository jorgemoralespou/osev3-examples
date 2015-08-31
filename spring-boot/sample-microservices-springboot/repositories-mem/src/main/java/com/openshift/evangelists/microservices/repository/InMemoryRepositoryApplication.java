package com.openshift.evangelists.microservices.repository;

import com.openshift.evangelists.microservices.api.MessageRepository;
import com.openshift.evangelists.microservices.repository.impl.InMemoryMessageRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by jmorales on 8/28/15.
 */
@SpringBootApplication
public class InMemoryRepositoryApplication {

    @Bean
    public MessageRepository messageRepository() {
        return new InMemoryMessageRepository();
    }
    public static void main(String[] args) {
        SpringApplication.run(InMemoryRepositoryApplication.class, args);
    }
}
