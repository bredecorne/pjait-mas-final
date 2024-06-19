package com.github.bredecorne.masp.model.taxes;

import com.sun.source.tree.VariableTree;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class ProgressiveTaxTest {

    @Test
    void createsAProgressiveTaxSuccessfullyAndCalculatesTaxProperly() {
        var scale = new TreeMap<>(Map.of(
                BigDecimal.ZERO, new BigDecimal("0.10"),
                new BigDecimal("500"), new BigDecimal("0.50"),
                new BigDecimal("750"), new BigDecimal("1")
         ));
        var income = new BigDecimal("1000");
        var expectedIncomeAfterTax = new BigDecimal("275");
        
        var progressiveTax = new ProgressiveTax(scale);

        assertEquals(0, expectedIncomeAfterTax.compareTo(progressiveTax.calculateTax(income)));
    }
}