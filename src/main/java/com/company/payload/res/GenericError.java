package com.company.payload.res;

import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
public class GenericError {
    private final String errorPath;
    private final String errorMessage;
    private final Integer errorCode;
    private final Object errorBody;
    private final Timestamp time;

    public GenericError(String errorPath, String errorMessage, Integer errorCode) {
        this(errorPath, errorMessage, null, errorCode);
    }

    public GenericError(String errorPath, String errorMessage, Object errorBody, Integer errorCode) {
        this.errorPath = errorPath;
        this.errorMessage = errorMessage;
        this.errorBody = errorBody;
        this.errorCode = errorCode;
        this.time = Timestamp.valueOf(LocalDateTime.now());
    }
}
