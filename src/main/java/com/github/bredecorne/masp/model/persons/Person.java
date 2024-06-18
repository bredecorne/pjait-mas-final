package com.github.bredecorne.masp.model.persons;

import com.github.bredecorne.masp.model.Address;
import com.github.bredecorne.masp.model.Status;

import java.math.BigDecimal;
import java.util.HashSet;

public abstract class Person {
    
    // Atrybuty wymagane
    private String name;
    private Status status;
    private BigDecimal feeRate;
    private boolean preferential;
    
    // Atrybuty opcjonalne
    private BigDecimal loyaltyPoints;
    private BigDecimal discountRate;
    
    // Atrybuty klasowe
    private static BigDecimal PREFERENTIAL_THRESHOLD;
    
    // Ekstensja
    private static final HashSet<Person> persons = new HashSet<>();
    
    // Asocjacje wiele-do-wiele
    private final HashSet<Address> addresses = new HashSet<>();


    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public BigDecimal getFeeRate() {
        return feeRate;
    }
}
