package com.fakepay.wallet.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void testValidTransactionDto() {
        TransactionDto dto = new TransactionDto("123456", new BigDecimal("100.00"));

        Set<ConstraintViolation<TransactionDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Expected no constraint violations for a valid TransactionDto");
    }

    @Test
    public void testDocumentShouldNotBeEmpty() {
        TransactionDto dto = new TransactionDto("", new BigDecimal("100.00"));

        Set<ConstraintViolation<TransactionDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Document cannot be null or empty", violations.iterator().next().getMessage());
    }

    @Test
    public void testDocumentSizeConstraint() {
        TransactionDto dto = new TransactionDto("123456789012345", new BigDecimal("100.00"));

        Set<ConstraintViolation<TransactionDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Document must be at most 14 characters", violations.iterator().next().getMessage());
    }

    @Test
    public void testTransactionValueShouldNotBeNull() {
        TransactionDto dto = new TransactionDto("123456", null);

        Set<ConstraintViolation<TransactionDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Deposit value cannot be null", violations.iterator().next().getMessage());
    }

    @Test
    public void testTransactionValueMustBePositive() {
        TransactionDto dto = new TransactionDto("123456", new BigDecimal("-50.00"));

        Set<ConstraintViolation<TransactionDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Deposit value must be a positive number", violations.iterator().next().getMessage());
    }
}