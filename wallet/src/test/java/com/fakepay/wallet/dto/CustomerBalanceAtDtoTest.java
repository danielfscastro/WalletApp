package com.fakepay.wallet.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerBalanceAtDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void testValidCustomerBalanceAtDto() {
        CustomerBalanceAtDto dto = new CustomerBalanceAtDto("12345678901", new BigDecimal("1000.00"), LocalDate.now());

        Set<ConstraintViolation<CustomerBalanceAtDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Expected no constraint violations for valid DTO");
    }

    @Test
    public void testDocumentShouldNotBeNull() {
        CustomerBalanceAtDto dto = new CustomerBalanceAtDto(null, new BigDecimal("1000.00"), LocalDate.now());

        Set<ConstraintViolation<CustomerBalanceAtDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Document Origin cannot be null", violations.iterator().next().getMessage());
    }

    @Test
    public void testDocumentSizeConstraints() {
        // Test too short document
        CustomerBalanceAtDto dtoShort = new CustomerBalanceAtDto("123", new BigDecimal("1000.00"), LocalDate.now());
        Set<ConstraintViolation<CustomerBalanceAtDto>> violationsShort = validator.validate(dtoShort);
        assertEquals(1, violationsShort.size());
        assertEquals("Document Origin must be between 1 and 20 characters", violationsShort.iterator().next().getMessage());

        // Test too long document
        CustomerBalanceAtDto dtoLong = new CustomerBalanceAtDto("123456789012345678901", new BigDecimal("1000.00"), LocalDate.now());
        Set<ConstraintViolation<CustomerBalanceAtDto>> violationsLong = validator.validate(dtoLong);
        assertEquals(1, violationsLong.size());
        assertEquals("Document Origin must be between 1 and 20 characters", violationsLong.iterator().next().getMessage());
    }

    @Test
    public void testBalanceMustBeADigit() {
        CustomerBalanceAtDto dto = new CustomerBalanceAtDto("12345678901", new BigDecimal("1000000000000000000.00"), LocalDate.now());

        Set<ConstraintViolation<CustomerBalanceAtDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Balance must be a number with up to 10 total digits and 2 decimal places", violations.iterator().next().getMessage());
    }

    @Test
    public void testBalanceAtShouldNotBeNull() {
        CustomerBalanceAtDto dto = new CustomerBalanceAtDto("12345678901", new BigDecimal("1000.00"), null);

        Set<ConstraintViolation<CustomerBalanceAtDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Created At cannot be null", violations.iterator().next().getMessage());
    }
}