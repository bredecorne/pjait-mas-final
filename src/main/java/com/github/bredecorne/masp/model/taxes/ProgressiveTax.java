package com.github.bredecorne.masp.model.taxes;

import java.math.BigDecimal;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class ProgressiveTax extends Tax {
    private final NavigableMap<BigDecimal, BigDecimal> value = new TreeMap<>(Map.of(
            BigDecimal.ZERO, new BigDecimal("0.12"),
            new BigDecimal("120000"), new BigDecimal("0.32")
    ));


    @Override
    public BigDecimal calculateTax(BigDecimal value) {
        var remainingIncome = value;
        var afterTaxIncome = BigDecimal.ZERO;

        for (Map.Entry<BigDecimal, BigDecimal> step : this.value.descendingMap().entrySet()) {
            var threshold = step.getKey();
            var rate = BigDecimal.ONE.subtract(step.getValue());

            if (remainingIncome.compareTo(threshold) >= 0) {
                afterTaxIncome = afterTaxIncome.add(
                        remainingIncome.subtract(threshold).multiply(rate)
                );
                remainingIncome = threshold;
            } else {
                afterTaxIncome = afterTaxIncome.add(
                        remainingIncome.subtract(threshold).multiply(rate)
                );
                break;
            }
        }

        return afterTaxIncome;
    }
}
