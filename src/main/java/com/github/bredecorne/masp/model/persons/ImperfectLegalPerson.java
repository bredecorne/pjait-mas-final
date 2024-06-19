package com.github.bredecorne.masp.model.persons;

import com.github.bredecorne.masp.model.Status;

import java.math.BigDecimal;

public class ImperfectLegalPerson extends Person {

    /**
     * Konstruktor dla obiektu typu ImperfectLegalPerson.
     *
     * @param name                  Nazwa danej osoby.
     * @param status                Aktualny status osoby.
     * @param feeRate               Wartość prowizji.
     * @param preferential          Status klienta.
     * @param preferentialAttribute Wartość zależna od statusu klienta – dla klienta o statusie preferential == true
     *                              określa atrybut reprezentujący nadaną zniżkę; w przeciwnym razie określa atrybut
     *                              reprezentujący liczbę zebranych punktów lojalnościowych.
     */
    public ImperfectLegalPerson(String name, Status status, BigDecimal feeRate, boolean preferential,
                                BigDecimal preferentialAttribute) {
        super(name, status, feeRate, preferential, preferentialAttribute);
    }

}
