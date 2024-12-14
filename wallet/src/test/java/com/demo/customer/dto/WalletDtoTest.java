package com.demo.customer.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class WalletDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void testValidWalletDto() {
        WalletDto dto = new WalletDto();
        dto.setWalletNumber(3454433243L);
        dto.setBalance(new BigDecimal("1000.00"));

        Set<ConstraintViolation<WalletDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Expected no constraint violations for a valid WalletDto");
    }

    @Test
    public void testWalletNumberShouldNotBeNull() {
        WalletDto dto = new WalletDto();
        dto.setWalletNumber(null);
        dto.setBalance(new BigDecimal("1000.00"));

        Set<ConstraintViolation<WalletDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Wallet Number cannot be null", violations.iterator().next().getMessage());
    }

    @Test
    public void testWalletNumberDigitsConstraint() {
        WalletDto dto = new WalletDto();
        dto.setWalletNumber(345443324222L); // 9 digits, should fail
        dto.setBalance(new BigDecimal("1000.00"));

        Set<ConstraintViolation<WalletDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Wallet Number must be a 10-digit number", violations.iterator().next().getMessage());
        
        // Test too long number
        dto.setWalletNumber(34544332456L); // 11 digits, should also fail
        violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Wallet Number must be a 10-digit number", violations.iterator().next().getMessage());
    }

    @Test
    public void testBalanceShouldNotBeNull() {
        WalletDto dto = new WalletDto();
        dto.setWalletNumber(3454433243L);
        dto.setBalance(null);

        Set<ConstraintViolation<WalletDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Balance cannot be null", violations.iterator().next().getMessage());
    }

    @Test
    public void testBalanceDigitsConstraint() {
        WalletDto dto = new WalletDto();
        dto.setWalletNumber(3454433243L);
        dto.setBalance(new BigDecimal("1000000000000000000.00000000")); // Exceeds digits before decimal

        Set<ConstraintViolation<WalletDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Balance must be a number with up to 10 total digits and 2 decimal places", violations.iterator().next().getMessage());
    }
}