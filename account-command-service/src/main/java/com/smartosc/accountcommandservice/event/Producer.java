package com.smartosc.accountcommandservice.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class Producer {

        private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendEvent(String topic, String event) {
        kafkaTemplate.send(topic, event);
    }
}