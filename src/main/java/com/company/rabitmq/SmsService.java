package com.company.rabitmq;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ToString
public class SmsService {
    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromPhoneNumber;

    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
    }

    public void sendSms(String to, Integer code) {
//        String body = "Your code from Fast Food: %s".formatted(code);
//        Message message1 = Message.creator(
//                new PhoneNumber(to),
//                new PhoneNumber(fromPhoneNumber),
//                body
//        ).create();
    }
}
