package com.github.bredecorne.masp.model;

import java.util.HashSet;

public class TaxOffice {
    
    // Atrybuty wymagane
    private String name;
    
    // Ekstensja
    private static final HashSet<TaxOffice> taxOffices = new HashSet<>();
    
    // Asocjacje jeden-do-wiele (po stronie jeden)
    private Address address;
}
