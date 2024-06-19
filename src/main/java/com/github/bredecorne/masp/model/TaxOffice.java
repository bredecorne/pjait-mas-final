package com.github.bredecorne.masp.model;

import java.io.Serializable;
import java.util.HashSet;


/**
 * Klasa reprezentująca urząd podatkowy.
 * <p>
 * Zawiera podstawowe dane urzędu – nazwę i jego adres.
 * Zarządza również ekstensją wszystkich utworzonych obiektów.
 * </p>
 */
public class TaxOffice implements Serializable {

    // Ekstensja
    private static HashSet<TaxOffice> taxOffices = new HashSet<>();
    // Atrybuty wymagane
    private final String name;
    // Asocjacje jeden-do-wiele (po stronie jeden)
    private Address address;


    /**
     * Konstruktor tworzący nowy obiekt TaxOffice z podaną nazwą i adresem.
     *
     * @param name    Nazwa urzędu skarbowego (nie może być pusta).
     * @param address Adres urzędu skarbowego (nie może być null).
     * @throws IllegalArgumentException Jeśli nazwa jest pusta lub adres jest null.
     */
    public TaxOffice(String name, Address address) {
        if (name.isEmpty() || address == null) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.address = address;

        taxOffices.add(this); // Dodaje do ekstensji
    }

    /**
     * Konstruktor tworzący nowy obiekt TaxOffice z podaną nazwą.
     * Adres pozostaje nieustawiony (null).
     *
     * @param name Nazwa urzędu skarbowego (nie może być pusta).
     * @throws IllegalArgumentException Jeśli nazwa jest pusta.
     */
    public TaxOffice(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.name = name;

        taxOffices.add(this); // Dodaje do ekstensji
    }
    
    public static HashSet<TaxOffice> getTaxOffices() {
        return new HashSet<TaxOffice>(taxOffices);
    }

    public static void setTaxOffices(HashSet<TaxOffice> taxOffices) {
        TaxOffice.taxOffices = taxOffices;
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
     * Dopuszcza zmianę tylko, jeżeli adres nie jest taki sam jak aktualny oraz, jeżeli dostarczana jest wartość inna
     * niż null, wywołuje metodę tworzącą asocjację po stronie adresu.
     * </p>
     *
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
