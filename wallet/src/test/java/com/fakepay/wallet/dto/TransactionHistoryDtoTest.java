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

public class TransactionHistoryDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void testValidTransactionHistoryDto() {
        TransactionHistoryDto dto = new TransactionHistoryDto(
                "deposit",
                new BigDecimal("100.00"),
                "USD",
                "12345678901234",
                "12345678901234",
                LocalDate.now()
        );

        Set<ConstraintViolation<TransactionHistoryDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Expected no constraint violations for a valid TransactionHistoryDto");
    }

    @Test
    public void testTransactionTypeShouldNotBeNull() {
        TransactionHistoryDto dto = new TransactionHistoryDto(
                null,
                new BigDecimal("100.00"),
                "USD",
                "12345678901234",
                "12345678901234",
                LocalDate.now()
        );

        Set<ConstraintViolation<TransactionHistoryDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Transaction Type cannot be null", violations.iterator().next().getMessage());
    }

    @Test
    public void testTransactionTypePattern() {
        TransactionHistoryDto dto = new TransactionHistoryDto(
                "invalidType",
                new BigDecimal("100.00"),
                "USD",
                "12345678901234",
                "12345678901234",
                LocalDate.now()
        );

        Set<ConstraintViolation<TransactionHistoryDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Transaction Type must be 'deposit', 'withdraw', or 'transfer'", violations.iterator().next().getMessage());
    }

    @Test
    public void testTransactionValueShouldNotBeNull() {
        TransactionHistoryDto dto = new TransactionHistoryDto(
                "deposit",
                null,
                "USD",
                "12345678901234",
                "12345678901234",
                LocalDate.now()
        );

        Set<ConstraintViolation<TransactionHistoryDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Transaction Value cannot be null", violations.iterator().next().getMessage());
    }

    @Test
    public void testTransactionValueMustBeGreaterThanZero() {
        TransactionHistoryDto dto = new TransactionHistoryDto(
                "deposit",
                new BigDecimal("0.00"), // 0 is not allowed
                "USD",
                "12345678901234",
                "12345678901234",
                LocalDate.now()
        );

        Set<ConstraintViolation<TransactionHistoryDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Transaction Value must be greater than 0", violations.iterator().next().getMessage());
    }

    @Test
    public void testTransactionValueDigitsConstraint() {
        TransactionHistoryDto dto = new TransactionHistoryDto(
                "deposit",
                new BigDecimal("1000000000000000000.00000000"), // Exceeds the digits before the decimal
                "USD",
                "12345678901234",
                "12345678901234",
                LocalDate.now()
        );

        Set<ConstraintViolation<TransactionHistoryDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Transaction Value must have at most 18 digits before the decimal and 8 digits after the decimal", violations.iterator().next().getMessage());
    }

    @Test
    public void testTransactionCurrencyShouldNotBeNullOrEmpty() {
        TransactionHistoryDto dto = new TransactionHistoryDto(
                "deposit",
                new BigDecimal("100.00"),
                null,
                "12345678901234",
                "12345678901234",
                LocalDate.now()
        );

        Set<ConstraintViolation<TransactionHistoryDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Transaction Currency cannot be null", violations.iterator().next().getMessage());
    }

}