package com.fh.scm.exceptions;

import lombok.Getter;

@Getter
public class RatingSupplierException extends RuntimeException {

    private int code;

    public RatingSupplierException(String message) {
        super(message);
    }

    public RatingSupplierException(String message, int code) {
        super(message);
        this.code = code;
    }
}
