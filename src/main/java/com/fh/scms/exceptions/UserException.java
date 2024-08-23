package com.fh.scms.exceptions;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

    private int code;

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, int code) {
        super(message);
        this.code = code;
    }
}
