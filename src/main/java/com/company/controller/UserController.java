package com.company.controller;

import com.company.payload.req.ReqUserCreate;
import com.company.payload.req.user.ReqActivateUser;
import com.company.payload.req.user.ReqResendCode;
import com.company.payload.req.user.ReqToken;
import com.company.payload.res.GenericResponse;
import com.company.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public GenericResponse<String> register(@RequestBody ReqUserCreate req) {
        return new GenericResponse<>(userService.register(req));
    }

    @PostMapping("/activate")
    public GenericResponse<String> activate(@RequestBody ReqActivateUser req) {
        log.info("Activate request");
        return new GenericResponse<>(userService.activate(req));
    }

    @PostMapping("/token/access")
    public GenericResponse<String> accessToken(@RequestBody ReqToken req) {
        return new GenericResponse<>(userService.accessToken(req));
    }

    @PostMapping("/code/resend")
    public GenericResponse<String> reSendCode(@RequestBody ReqResendCode req) {
        log.info("Post to sms reSender");
        return new GenericResponse<>(userService.resendCodeIfExpired(req));
    }
}
