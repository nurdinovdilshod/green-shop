package com.company.service;

import com.company.entity.UserSms;
import com.company.repositories.UserSmsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSmsService {
    private final UserSmsRepository userSmsRepository;
    private final Random random;

    public UserSms createSms(String userId) {
     /*   UserSms sms = getByUserId(userId);
        if (sms!=null){
            throw new RuntimeException("Sms already send ");
        }*/
        UserSms userSms = UserSms.builder()
                .fromTime(LocalDateTime.now())
                .toTime(LocalDateTime.now().plusMinutes(2))
                .code(random.nextInt(1_000, 9_999))
                .userId(userId)
                .build();
        log.info("Sms Code : {}", userSms.getCode());
        return userSmsRepository.save(userSms);
    }

    public UserSms findByUserId(String userId) {
        return userSmsRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Sms not found"));
    }

    public UserSms getByUserId(String userId) {
        return userSmsRepository.findByUserId(userId)
                .orElse(null);
    }
}
