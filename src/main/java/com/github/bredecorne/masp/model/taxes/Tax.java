package com.github.bredecorne.masp.model.taxes;

import com.github.bredecorne.masp.model.PeriodEntrySet;

import java.math.BigDecimal;
import java.util.HashSet;

public abstract class Tax {
    
    // Ekstensja
    private final static HashSet<Tax> taxes = new HashSet<>();
    
    // Asocjacje wiele-do-wiele
    private final HashSet<PeriodEntrySet> periodEntrySets = new HashSet<>();
    
    // Metody abstrakcyjne
    public abstract BigDecimal calculateTax(BigDecimal untaxedIncome);

    
    public Tax() {
        taxes.add(this);
    }
    
    
    public void addPeriodEntrySet(PeriodEntrySet periodEntrySet) {
        if (periodEntrySet == null) { throw new IllegalArgumentException(); }
        if (!periodEntrySets.contains(periodEntrySet)) {
            periodEntrySets.add(periodEntrySet);
            periodEntrySet.addTax(this);
        }
    }
    
    public void removePeriodEntrySet(PeriodEntrySet periodEntrySet) {
        if (!periodEntrySets.contains(periodEntrySet)) {
            periodEntrySets.remove(periodEntrySet);
            periodEntrySet.removeTax(this);
        }
    }
}
