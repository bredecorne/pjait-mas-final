package com.github.bredecorne.masp.model.taxes;

import com.github.bredecorne.masp.model.PeriodEntrySet;

import java.math.BigDecimal;
import java.util.HashSet;

public abstract class Tax {
    
    // Ekstensja
    private final static HashSet<Tax> taxes = new HashSet<>();
    
    // Asocjacje wiele-do-wiele
    private final HashSet<PeriodEntrySet> periodEntrySets = new HashSet<>();
    
    
    public abstract BigDecimal calculateTax(BigDecimal value);
}
