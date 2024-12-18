package com.fakepay.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Custom exception thrown when a Transaction History resource is not found.
 * This exception returns a 404 NOT FOUND HTTP status when thrown.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TransactionHistoryNotFoundException extends RuntimeException {

    /**
     * Constructor for TransactionHistoryNotFoundException.
     *
     * @param resourceName The name of the resource that was not found.
     * @param fieldValues  A map containing field names and their corresponding values that were searched.
     */
    public TransactionHistoryNotFoundException(String resourceName, Map<String, String> fieldValues) {
        super(String.format("%s not found with the given input data: %s",
                resourceName,
                fieldValues.entrySet()
                        .stream()
                        .map(entry -> entry.getKey() + ": '" + entry.getValue() + "'")
                        .collect(Collectors.joining(", "))));
    }
}