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

public class TransferTransactionDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void testValidTransferTransactionDto() {
        TransferTransactionDto dto = new TransferTransactionDto(
                "12345678901234",
                 "12345678901234",
                new BigDecimal("100.00")
        );

        Set<ConstraintViolation<TransferTransactionDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Expected no constraint violations for a valid TransferTransactionDto");
    }

    @Test
    public void testDocumentOriginShouldNotBeEmpty() {
        TransferTransactionDto dto = new TransferTransactionDto(
                "",
                "12345678901234",
                new BigDecimal("100.00")
        );

        Set<ConstraintViolation<TransferTransactionDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Document cannot be null or empty", violations.iterator().next().getMessage());
    }

    @Test
    public void testDocumentDestinationShouldNotBeEmpty() {
        TransferTransactionDto dto = new TransferTransactionDto(
               "12345678901234",
              "",
                new BigDecimal("100.00")
        );

        Set<ConstraintViolation<TransferTransactionDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Document cannot be null or empty", violations.iterator().next().getMessage());
    }

    @Test
    public void testDocumentSizeConstraint() {
        // Test too short document
        TransferTransactionDto dto1 = new TransferTransactionDto(
                "1234",
                "12345678901234111",
                new BigDecimal("100.00")
        );

        Set<ConstraintViolation<TransferTransactionDto>> violations1 = validator.validate(dto1);
        assertEquals(1, violations1.size());
        assertEquals("Document must be at most 14 characters", violations1.iterator().next().getMessage());

        // Test too long document
        TransferTransactionDto dto2 = new TransferTransactionDto(
                "1234567890123456",
                "12345678901234",
                new BigDecimal("100.00")
        );

        Set<ConstraintViolation<TransferTransactionDto>> violations2 = validator.validate(dto2);
        assertEquals(1, violations2.size());
        assertEquals("Document must be at most 14 characters", violations2.iterator().next().getMessage());
    }

    @Test
    public void testTransactionValueShouldNotBeNull() {
        TransferTransactionDto dto = new TransferTransactionDto(
                "12345678901234",
                "12345678901234",
                null
        );

        Set<ConstraintViolation<TransferTransactionDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Deposit value cannot be null", violations.iterator().next().getMessage());
    }

    @Test
    public void testTransactionValueMustBeGreaterThanZero() {
        TransferTransactionDto dto = new TransferTransactionDto(
                "12345678901234",
               "12345678901234",
                new BigDecimal("0.00") // 0 is not allowed
        );

        Set<ConstraintViolation<TransferTransactionDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Deposit value must be a positive number", violations.iterator().next().getMessage());
    }
}