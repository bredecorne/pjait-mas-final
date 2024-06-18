package com.github.bredecorne.masp.model;

import com.github.bredecorne.masp.model.persons.Person;

import java.util.HashSet;

public class Address {
    
    // Atrybuty wymagane
    private Country country;
    private String street;
    private String houseNumber;
    
    // Atrybuty opcjonalne
    private String apartmentNumber;
    
    // Ekstensja
    private static final HashSet<Address> addresses = new HashSet<>();

    // Asocjacje wiele-do-wiele
    private final HashSet<Person> persons = new HashSet<>();
    
    // Asocjacje jeden-do-wiele (po stronie wiele)
    private final HashSet<TaxOffice> taxOffices = new HashSet<>();
}
