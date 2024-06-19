package com.github.bredecorne.masp.model.persons;

import com.github.bredecorne.masp.model.Status;

import java.math.BigDecimal;

public class LegalPerson extends Person {
    
    // Atrybuty wymagane
    private boolean reducedTax;

    /**
     * Konstruktor dla obiektu typu Person.
     *
     * @param name                  Nazwa danej osoby.
     * @param status                Aktualny status osoby.
     * @param feeRate               Wartość prowizji.
     * @param preferential          Status klienta.
     * @param preferentialAttribute Wartość zależna od statusu klienta – dla klienta o statusie preferential == true
     *                              określa atrybut reprezentujący nadaną zniżkę; w przeciwnym razie określa atrybut
     *                              reprezentujący liczbę zebranych punktów lojalnościowych.
     * @param reducedTax            Wartość logiczna – przyjmuje true, jeżeli klient korzysta z ulgi w podatku;
     *                              false w przeciwnym razie
     */
    public LegalPerson(String name, Status status, BigDecimal feeRate, 
                       boolean preferential, BigDecimal preferentialAttribute, boolean reducedTax) {
        super(name, status, feeRate, preferential, preferentialAttribute);
        this.reducedTax = reducedTax;
    }

    public boolean isReducedTax() {
        return reducedTax;
    }

    public void setReducedTax(boolean reducedTax) {
        this.reducedTax = reducedTax;
    }
}
