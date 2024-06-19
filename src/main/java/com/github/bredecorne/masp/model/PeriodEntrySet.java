package com.github.bredecorne.masp.model;

import com.github.bredecorne.masp.model.persons.Person;
import com.github.bredecorne.masp.model.taxes.Tax;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashSet;

public class PeriodEntrySet {
    
    // Atrybuty wymagane
    private HashSet<Entry> entries;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;
    
    // Ekstensja
    private final HashSet<PeriodEntrySet> periodEntrySets = new HashSet<>();
    
    // Asocjacje wiele-do-wiele
    private final HashSet<AccountManager> accountManagers = new HashSet<>();
    private final HashSet<Tax> taxes = new HashSet<>();
    
    // Asocjacje jeden-do-wiele (po stronie jeden)
    private final Person person;


    public PeriodEntrySet(LocalDate dateFrom, LocalDate dateTo, Person person, HashSet<Entry> entries) {
        if (dateFrom == null || dateTo == null || 
            person == null || entries == null) { throw new IllegalArgumentException(); }
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.person = person;
        this.entries = entries;
    }

    public HashSet<Entry> getEntries() {
        return entries;
    }

    public void setEntries(HashSet<Entry> entries) {
        if (entries == null) { throw new IllegalArgumentException(); }
        this.entries = entries;
    }

    /**
     * Określa wartość atrybutu pochodnego abbreviation, który stanowi podstawę asocjacji kwalifikowanej
     * z klasą reprezentującą menedżera konta.
     * W określaniu wartości uwzględnia zakres dat oraz nazwę klienta.
     * @return abbreviation Ciąg znaków złożony z kolejno: nazwy klienta, daty początkowej zbioru wpisów księgowych
     * i daty końcowej zbioru wpisów księgowych (np. ABC-2024-01-01-TO-2024-02-27)
     */
    public String getAbbreviation() {
        return String.join(person.getName(), "-", dateFrom.toString(), "-TO-", dateTo.toString());
    }
    
    private BigDecimal sum(boolean positive) {
        var sum = new BigDecimal(BigInteger.ZERO);

        for (Entry entry : entries) {
            if (positive && entry.getValue().compareTo(BigDecimal.ZERO) > 0) {
                sum = sum.add(entry.getValue());
            } else {
                sum = sum.add(entry.getValue());
            }
        }

        return sum;
    }
    
    public BigDecimal getRevenue() {
        return sum(true);
    }

    public BigDecimal getExpenses() {
        return sum(false);
    }

    public BigDecimal getIncome() {
        return getRevenue().subtract(getExpenses());
    }

    public BigDecimal getAfterTaxIncome(Tax applicableTax) {
        return applicableTax.calculateTax(getIncome());
    }

    public BigDecimal getFee(Tax applicableTax) {
        return getAfterTaxIncome(applicableTax).multiply(BigDecimal.ONE.subtract(person.getFeeRate()));
    }
    
    public void addTax(Tax tax) {
        if (tax == null) { throw new IllegalArgumentException(); }
        if (!taxes.contains(tax)) {
            taxes.add(tax);
            tax.addPeriodEntrySet(this);
        }
    }
    
    public void removeTax(Tax tax) {
        if (taxes.contains(tax)) {
            taxes.remove(tax);
            tax.removePeriodEntrySet(this);
        }
    }
    
    
    public class Entry {
        
        // Atrybuty wymagane
        private final LocalDate date;
        private final BigDecimal value;
        
        // Atrybuty opcjonalne
        private String justification;

        
        /**
         * Tworzy obiekt reprezentujący wpis księgowy, uwzględniając atrybut opcjonalny justification.
         * @param date Data transakcji stanowiącej podstawę wpisu księgowego, niebędąca wartością null.
         * @param value Wartość transakcji stanowiącej podstawę wpisu księgowego, niebędąca wartością null.
         * @param justification Uzasadnienie biznesowe dokonywanego wpisu księgowego.
         */
        public Entry(LocalDate date, BigDecimal value, String justification) {
            if (date == null || value == null || 
                value.equals(BigDecimal.ZERO)) { throw new IllegalArgumentException(); }
            this.date = date;
            this.value = value;
            this.justification = justification;
        }

        /**
         * Tworzy obiekt reprezentujący wpis księgowy z pominięciem atrybutu opcjonalnego justification.
         * @param date Data transakcji stanowiącej podstawę wpisu księgowego, niebędąca wartością null.
         * @param value Wartość transakcji stanowiącej podstawę wpisu księgowego, niebędąca wartością null.
         */
        public Entry(LocalDate date, BigDecimal value) {
            if (date == null || value == null || 
                value.equals(BigDecimal.ZERO)) { throw new IllegalArgumentException(); }
            this.date = date;
            this.value = value;
        }

        
        public LocalDate getDate() {
            return date;
        }

        public BigDecimal getValue() {
            return value;
        }

        public String getJustification() {
            return justification;
        }

        /**
         * Zmienia lub określa uzasadnienie biznesowe transakcji, stanowiącej podstawę wpisu księgowego.
         * @param justification Uzasadnienie biznesowe dokonywanego wpisu księgowego.
         */
        public void setJustification(String justification) {
            this.justification = justification;
        }
    }
}
