package com.company.service;

import com.company.config.security.JwtTokenUtil;
import com.company.config.security.SessionUser;
import com.company.entity.AuthUser;
import com.company.entity.UserSms;
import com.company.enums.UserRole;
import com.company.enums.UserStatus;
import com.company.payload.req.ReqUserCreate;
import com.company.payload.req.user.ReqActivateUser;
import com.company.payload.req.user.ReqResendCode;
import com.company.payload.req.user.ReqToken;
import com.company.rabitmq.Producer;
import com.company.rabitmq.SendSmsEvent;
import com.company.repositories.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSmsService userSmsService;
    private final Producer producer;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final SessionUser sessionUser;

    public String register(ReqUserCreate req) {
        if (checkPhoneNumberExist(req.getPhoneNumber()) != null) {
            throw new RuntimeException("User already exist");
        }

        AuthUser authUser = AuthUser.childBuilder()
                .name(req.getName())
                .password(passwordEncoder.encode(req.getPassword()))
                .phoneNumber(req.getPhoneNumber())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy(sessionUser.id())
                .role(UserRole.CUSTOMER)
                .build();
        if (sessionUser.id() == null) {
            authUser.setCreatedBy(sessionUser.id());
        }
        AuthUser user = authUserRepository.save(authUser);
        UserSms userSms = userSmsService.createSms(user.getId());
        producer.sendMessage(new SendSmsEvent(authUser.getPhoneNumber(), userSms.getCode()));
        return "User Successfully registered";
    }

    public String activate(ReqActivateUser req) {
        AuthUser authUser = findByPhoneNumber(req.getPhoneNumber());
        UserSms userSms = userSmsService.findByUserId(authUser.getId());
        if (!userSms.getCode().equals(req.getCode())) {
            throw new RuntimeException("Invalid activation code");
        }
        authUser.setStatus(UserStatus.ACTIVE);
        authUserRepository.save(authUser);
        return "Activated successfully 😀";
    }

    public String accessToken(ReqToken req) {
        String phoneNumber = req.getPhoneNumber();
        findByPhoneNumber(phoneNumber);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(phoneNumber, req.getPassword());
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateJwtToken(phoneNumber);
    }

    public String resendCodeIfExpired(ReqResendCode req) {
        String phoneNumber = req.getPhoneNumber();
        AuthUser authUser = findByPhoneNumber(phoneNumber);
        String userId = authUser.getId();
        UserSms byUserId = userSmsService.getByUserId(userId);
        if (byUserId != null) {
            return "Sms sent";
        }
        UserSms userSms = userSmsService.createSms(userId);
        producer.sendMessage(new SendSmsEvent(phoneNumber, userSms.getCode()));
        return "Sms resent again";
    }

    public AuthUser checkPhoneNumberExist(String phoneNumber) {
        return authUserRepository.findByPhoneNumber(phoneNumber).orElse(null);
    }

    public AuthUser findByPhoneNumber(String phoneNumber) {
        return authUserRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new RuntimeException("User not found with this phone Number '%s' ".formatted(phoneNumber)));
    }


}
