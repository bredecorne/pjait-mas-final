package com.github.bredecorne.masp.model.persons;

import com.github.bredecorne.masp.model.Status;

import java.math.BigDecimal;

public class NaturalPerson extends Person {
    
    // Atrybuty wymagane
    private boolean uniformTax;

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
     * @param uniformTax            Wartość logiczna – przyjmuje true, jeżeli klient korzysta z podatku liniowego;
     *                              false w przeciwnym razie
     */
    public NaturalPerson(String name, Status status, BigDecimal feeRate, 
                         boolean preferential, BigDecimal preferentialAttribute, boolean uniformTax) {
        super(name, status, feeRate, preferential, preferentialAttribute);
        this.uniformTax = uniformTax;
    }

    public boolean isUniformTax() {
        return uniformTax;
    }

    public void setUniformTax(boolean uniformTax) {
        this.uniformTax = uniformTax;
    }
}
