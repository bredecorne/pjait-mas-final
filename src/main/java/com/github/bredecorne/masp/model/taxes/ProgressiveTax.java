package com.github.bredecorne.masp.model.taxes;

import java.math.BigDecimal;
import java.util.NavigableMap;

public class ProgressiveTax extends Tax {
    private final NavigableMap<BigDecimal, BigDecimal> value;

    public ProgressiveTax(NavigableMap<BigDecimal, BigDecimal> value) {
        super();
        if (value == null) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    /**
     * Oblicza wartość podatku zgodnie ze skalą progresywną – korzysta z progów podatkowych.
     *
     * @param untaxedIncome Kwota, od której zostanie obliczona wartość podatku.
     * @return Wartość podatku.
     */
    @Override
    public BigDecimal calculateTax(BigDecimal untaxedIncome) {
        var tax = BigDecimal.ZERO;
        var remaining = untaxedIncome;

        for (var entry : value.descendingMap().entrySet()) {
            var threshold = entry.getKey();
            var rate = entry.getValue();

            if (remaining.compareTo(threshold) >= 0) {
                if (threshold.equals(BigDecimal.ZERO)) {
                    tax = tax.add(remaining.multiply(rate));
                } else {
                    tax = tax.add(remaining.subtract(threshold).multiply(rate));
                    remaining = remaining.subtract(threshold);
                }
            }
        }

        return tax;
    }
}
