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
    
    // Asocjacje jeden-do-wiele (po stronie wiele)
    private final HashSet<Address> addresses = new HashSet<>();

    /**
     * Konstruktor tworzący nowy obiekt TaxOffice z podaną nazwą.
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

    public HashSet<Address> getAddresses() {
        return new HashSet<>(addresses);
    }

    /**
     * Tworzy nowe powiązanie z adresem.
     * Wywołuje analogiczną metodę po stronie adresu.
     * @param address Adres, niebędący wartością null.
     */
    public void addAddress(Address address) {
        if (address == null) { throw new IllegalArgumentException(); }
        if (!addresses.contains(address)) {
            addresses.add(address);
            address.setTaxOffice(this);
        }
    }

    /**
     * Usuwa powiązanie z adresem.
     * @param address Adres, z którym istnieje już powiązanie.
     */
    public void removeAddress(Address address) {
        if (address == null) { throw new IllegalArgumentException(); }
        if (addresses.contains(address)) {
            addresses.remove(address);
            address.setTaxOffice(null);
        }
    }
}
