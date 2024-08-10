package com.company.payload.res;

import lombok.Getter;

@Getter
public class GenericResponse<T> {
    private final T data;
    private final GenericError error;
    private final String message;
    private final boolean success;
    public GenericResponse(T data) {
        this(data, null);
    }

    public GenericResponse(GenericError error) {
        this(error, null);
    }

    public GenericResponse(T data, String message) {
        this(data, null, message, true);
    }

    public GenericResponse(GenericError error, String message) {
        this(null, error, message, false);
    }

    private GenericResponse(T data, GenericError error, String message, boolean success) {
        this.data = data;
        this.error = error;
        this.message = message;
        this.success = success;
    }
}
