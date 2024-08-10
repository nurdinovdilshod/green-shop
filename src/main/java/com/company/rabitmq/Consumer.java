package com.company.rabitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class Consumer {
    private final SmsService smsService;

    @RabbitListener(queues = Config.QUEUE)
    public void sendSmsEventListener(SendSmsEvent message) {
        log.info("new message from Consumer: {}",message);
        smsService.sendSms(message.phoneNumber(), message.code());
    }
}
