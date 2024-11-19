package com.smartosc.userservice.controller;

import com.smartosc.userservice.producer.UserServiceProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StreamKafkaController {

    private final UserServiceProducer userServiceProducer;
}
