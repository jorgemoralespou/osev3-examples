package com.openshift.evangelists.microservices.web;

/**
 * Created by jmorales on 8/28/15.
 */
import com.openshift.evangelists.microservices.web.client.RestMessageRepositoryClient;
import com.openshift.evangelists.microservices.web.api.Message;
import com.openshift.evangelists.microservices.web.api.MessageRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;

@SpringBootApplication
public class SampleWebUIApplication {

    @Bean
    public MessageRepository messageRepository() {
        return new RestMessageRepositoryClient();
    }

    @Bean
    public Converter<String, Message> messageConverterFromId() {
        return new Converter<String, Message>() {
            @Override
            public Message convert(String id) {
                System.out.println("Converter.convertingFromId: "+ id);
                Message msg = messageRepository().findMessage(Long.valueOf(id));
                System.out.println("Converter.convertingTo Message: "+ msg);
                return msg;
            }
        };
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleWebUIApplication.class, args);
    }

}
