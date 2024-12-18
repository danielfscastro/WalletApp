package com.fakepay.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WalletAlreadyExistsException extends RuntimeException {

    public WalletAlreadyExistsException(String message) {
        super(message);
    }

}
