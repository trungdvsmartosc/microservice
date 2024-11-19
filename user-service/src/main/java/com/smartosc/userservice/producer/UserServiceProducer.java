package com.smartosc.userservice.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class UserServiceProducer {

    @Bean
    public Supplier<Message<String>> sendMessage() {
        return () -> MessageBuilder.withPayload("Hello from service producer").build();
    }
}
