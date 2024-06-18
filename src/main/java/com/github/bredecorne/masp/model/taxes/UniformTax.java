package com.github.bredecorne.masp.model.taxes;

import java.math.BigDecimal;

public class UniformTax extends Tax {
    private BigDecimal value;

    @Override
    public BigDecimal calculateTax(BigDecimal value) {
        return value.multiply(BigDecimal.ONE.subtract(this.value));
    }
}
