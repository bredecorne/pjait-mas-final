package com.github.bredecorne.masp.model;

import java.util.HashSet;

public class TaxOffice {
    
    // Atrybuty wymagane
    private String name;
    
    // Ekstensja
    private static final HashSet<TaxOffice> taxOffices = new HashSet<>();
    
    // Asocjacje jeden-do-wiele (po stronie jeden)
    private Address address;


    public TaxOffice(String name, Address address) {
        if (name.isEmpty() || address == null) { throw new IllegalArgumentException(); }
        this.name = name;
        this.address = address;
    }

    public TaxOffice(String name) {
        if (name.isEmpty()) { throw new IllegalArgumentException(); }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        if (!(address == null || address == this.address)) {
            this.address = address;
            address.addTaxOffice(this);
        }
    }
}
