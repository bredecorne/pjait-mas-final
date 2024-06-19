package com.github.bredecorne.masp.model.taxes;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class UniformTaxTest {

    @Test
    void createsAUniformTaxSuccessfullyAndCalculateTaxProperly() {
        var value = new BigDecimal("0.2");
        var income = new BigDecimal("100");
        var expectedTax = new BigDecimal("20");
        
        var uniformTax = new UniformTax(value);

        assertEquals(0, expectedTax.compareTo(uniformTax.calculateTax(income)));
    }

}