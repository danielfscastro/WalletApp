package com.fakepay.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerNotExistsException extends RuntimeException {

    public CustomerNotExistsException(String message) {
        super(message);
    }

}
