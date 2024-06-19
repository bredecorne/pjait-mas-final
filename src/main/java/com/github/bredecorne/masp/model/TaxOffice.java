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
}
