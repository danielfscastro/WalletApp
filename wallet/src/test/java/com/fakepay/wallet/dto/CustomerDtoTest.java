package com.fakepay.wallet.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void testValidCustomerDto() {
        CustomerDto dto = new CustomerDto("Daniel Castro", "example@example.com", "12345678901234", null);

        Set<ConstraintViolation<CustomerDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Expected no constraint violations for a valid CustomerDto");
    }

    @Test
    public void testNameShouldNotBeEmpty() {
        CustomerDto dto = new CustomerDto("", "example@example.com", "12345678901234", null);

        Set<ConstraintViolation<CustomerDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Customer name cannot be null or empty", violations.iterator().next().getMessage());
    }

    @Test
    public void testEmailShouldNotBeEmpty() {
        CustomerDto dto = new CustomerDto("Daniel Castro", "", "12345678901234", null);

        Set<ConstraintViolation<CustomerDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Email cannot be null or empty", violations.iterator().next().getMessage());
    }

    @Test
    public void testDocumentShouldNotBeEmpty() {
        CustomerDto dto = new CustomerDto("Daniel Castro", "example@example.com", "", null);

        Set<ConstraintViolation<CustomerDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Document cannot be null or empty", violations.iterator().next().getMessage());
    }

    @Test
    public void testNameSizeConstraint() {
        CustomerDto dto = new CustomerDto("This is a very long name that exceeds one hundred characters in length and should result in a validation error because it is not allowed", "example@example.com", "12345678901234", null);

        Set<ConstraintViolation<CustomerDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Customer name must be at most 100 characters", violations.iterator().next().getMessage());
    }

    @Test
    public void testEmailSizeConstraint() {
        // Create an email longer than 100 characters
        String longEmail = "this.is.a.verlong.email.address.that.exceeds.one.hundred.charactersffffffffffffffffffffff@example.com"; // This should exceed 100 characters

        CustomerDto dto = new CustomerDto("Daniel Castro", longEmail, "123456", null); // Use a valid document string within limits

        Set<ConstraintViolation<CustomerDto>> violations = validator.validate(dto);

        // Validate that we receive 1 violation
        assertEquals(1, violations.size(), "Expected exactly one violation for email being too long");

        // Validate the specific violation message
        assertEquals("Email must be at most 100 characters", violations.iterator().next().getMessage());
    }

    @Test
    public void testDocumentSizeConstraint() {
        CustomerDto dto = new CustomerDto("Daniel Castro", "example@example.com", "123456789012345", null);

        Set<ConstraintViolation<CustomerDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Document must be at most 14 characters", violations.iterator().next().getMessage());
    }
}