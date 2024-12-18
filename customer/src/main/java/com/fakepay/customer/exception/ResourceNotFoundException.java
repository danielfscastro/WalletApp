package com.fakepay.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor for ResourceNotFoundException.
     *
     * @param resourceName The name of the resource that was not found.
     * @param fieldName The name of the field that was searched.
     * @param fieldValue The value of the field that does not match an existing resource.
     */
    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s not found with the given input data %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
