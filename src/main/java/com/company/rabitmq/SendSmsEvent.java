package com.company.rabitmq;

public record SendSmsEvent(String phoneNumber, Integer code) {
}
