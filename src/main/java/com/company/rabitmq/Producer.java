package com.company.rabitmq;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Producer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(SendSmsEvent sendSmsEvent) {
        log.info("Sending json message: {}", sendSmsEvent);
        rabbitTemplate.convertAndSend(Config.EXCHANGE, Config.ROUTING_KEY, sendSmsEvent);
    }
}
