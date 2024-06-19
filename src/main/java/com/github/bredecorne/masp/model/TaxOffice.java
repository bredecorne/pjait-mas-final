package com.github.bredecorne.masp.model;

import java.io.Serializable;
import java.util.HashSet;

public class TaxOffice implements Serializable {
    
    // Atrybuty wymagane
    private String name;
    
    // Ekstensja
    private static HashSet<TaxOffice> taxOffices = new HashSet<>();
    
    // Asocjacje jeden-do-wiele (po stronie jeden)
    private Address address;


    public TaxOffice(String name, Address address) {
        if (name.isEmpty() || address == null) { throw new IllegalArgumentException(); }
        this.name = name;
        this.address = address;
        
        taxOffices.add(this); // Dodaje do ekstensji
    }

    public TaxOffice(String name) {
        if (name.isEmpty()) { throw new IllegalArgumentException(); }
        this.name = name;
        
        taxOffices.add(this); // Dodaje do ekstensji
    }

    
    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Zmienia adres, dla którego właściwy jest urząd.
     * <p>
     *      Dopuszcza zmianę tylko, jeżeli adres nie jest taki sam jak aktualny oraz, jeżeli dostarczana jest wartość inna
     *      niż null, wywołuje metodę tworzącą asocjację po stronie adresu.
     * </p>
     * @param address Obiekt typu adres, dla którego właściwy jest urząd lub wartość null, reprezentująca brak
     *                właściwości.
     */
    public void setAddress(Address address) {
        if (!(address == this.address)) {
            this.address = address;
            if (address != null) {
                address.addTaxOffice(this);
            }
        }
    }
    
    public static HashSet<TaxOffice> getTaxOffices() {
        return new HashSet<TaxOffice>(taxOffices);
    }

    public static void setTaxOffices(HashSet<TaxOffice> taxOffices) {
        TaxOffice.taxOffices = taxOffices;
    }
}
