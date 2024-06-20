package com.github.bredecorne.masp.model.taxes;

import com.github.bredecorne.masp.model.PeriodEntrySet;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;

public abstract class Tax implements Serializable {

    // Ekstensja
    private static HashSet<Tax> taxes = new HashSet<>();

    // Asocjacje wiele-do-wiele
    private final HashSet<PeriodEntrySet> periodEntrySets = new HashSet<>();

    public Tax() {
        taxes.add(this); // Dodaje do ekstensji.
    }

    
    /**
     * Zwraca kopię ekstensji klasy.
     * @return Kopia ekstensji klasy.
     */
    public static HashSet<Tax> getTaxes() {
        return new HashSet<>(taxes);
    }

    /**
     * Ustawia ekstensję klasy – wymagany ze względu na mechanizm serializacji.
     * @param taxes Ekstensja klasy.
     */
    public static void setTaxes(HashSet<Tax> taxes) {
        Tax.taxes = taxes;
    }

    // Metody abstrakcyjne
    public abstract BigDecimal calculateTax(BigDecimal untaxedIncome);


    /**
     * Dodaje powiązanie ze zbiorem wpisów księgowych.
     * Zwraca wyjątek w sytuacji, gdy podawany argument przyjmuje wartość null.
     * Wywołuje analogiczną metodę w obiekcie zbioru wpisu księgowego (po stronie wiele).
     * @param periodEntrySet Zbiór wpisów księgowych niebędący wartością null.
     */
    public void addPeriodEntrySet(PeriodEntrySet periodEntrySet) {
        if (periodEntrySet == null) {
            throw new IllegalArgumentException();
        }
        if (!periodEntrySets.contains(periodEntrySet)) {
            periodEntrySets.add(periodEntrySet);
            periodEntrySet.addTax(this);
        }
    }


    /**
     * Usuwa powiązanie ze zbiorem wpisów księgowych.
     * Wywołuje analogiczną metodę w obiekcie zbioru wpisu księgowego (po stronie wiele).
     * @param periodEntrySet Zbiór wpisów księgowych, z którym istnieje już powiązanie.
     */
    public void removePeriodEntrySet(PeriodEntrySet periodEntrySet) {
        if (!periodEntrySets.contains(periodEntrySet)) {
            periodEntrySets.remove(periodEntrySet);
            periodEntrySet.removeTax(this);
        }
    }
}
