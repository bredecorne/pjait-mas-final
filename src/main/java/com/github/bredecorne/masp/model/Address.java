package com.github.bredecorne.masp.model;

import com.github.bredecorne.masp.model.persons.Person;

import java.io.Serializable;
import java.util.HashSet;
import java.util.regex.Pattern;

public class Address implements Serializable {

    // Ekstensja
    private static HashSet<Address> addresses = new HashSet<>();
    
    // Asocjacje wiele-do-wiele
    private final HashSet<Person> persons = new HashSet<>();
    
    // Asocjacje jeden-do-wiele (po stronie jeden)
    private TaxOffice taxOffice;
    
    // Atrybuty wymagane
    private final Country country;
    private final String city;
    private final String street;
    private final String houseNumber;
    
    // Atrybuty opcjonalne
    private String apartmentNumber;

    public Address(Country country, String city, String street, String houseNumber, String apartmentNumber, 
                   TaxOffice taxOffice) {
        if (country == null || city == null || street == null ||
                isHouseNumberInvalid(houseNumber)) {
            throw new IllegalArgumentException();
        }
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
        this.taxOffice = taxOffice;

        addresses.add(this); // Dodaje do ekstensji
    }

    public Address(Country country, String city, String street, String houseNumber, String apartmentNumber) {
        if (country == null || city == null || street == null ||
                isHouseNumberInvalid(houseNumber)) {
            throw new IllegalArgumentException();
        }
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;

        addresses.add(this); // Dodaje do ekstensji
    }

    public Address(Country country, String city, String street, String houseNumber) {
        if (country == null || city == null || street == null ||
                isHouseNumberInvalid(houseNumber)) {
            throw new IllegalArgumentException();
        }
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;

        addresses.add(this); // Dodaje do ekstensji
    }

    /**
     * Metoda pomocnicza – weryfikuje poprawność zadanego numeru domu lub mieszkania.
     * <p>
     * Uznaje wartości poprawne w szczególności ciągi szczególne typu "14A", "14AB", "12/3AB".
     * </p>
     *
     * @param number Numer domu lub mieszkania w formacie String.
     * @return Wartość logiczna – true, jeżeli numer domu lub mieszkania jest niepoprawny; false przeciwnie.
     */
    private static boolean isHouseNumberInvalid(String number) {
        var pattern = Pattern.compile("^(\\d+)([A-Z]{0,2})([/-]\\d+)?([A-Z]{0,2})?$");
        return !pattern.matcher(number).matches();
    }

    public static HashSet<Address> getAddresses() {
        return new HashSet<>(addresses);
    }

    public static void setAddresses(HashSet<Address> addresses) {
        Address.addresses = addresses;
    }

    public TaxOffice getTaxOffice() {
        return taxOffice;
    }

    public void setTaxOffice(TaxOffice taxOffice) {
        this.taxOffice = taxOffice;
        if (taxOffice != null) {
            taxOffice.addAddress(this);
        }
    }

    /**
     * Tworzy związek powiązania z obiektem reprezentującym osobę.
     * <p>
     * Wywołuje analogiczną metodę po stronie osoby.
     * </p>
     *
     * @param person Osoba, wartość niebędąca null.
     */
    public void addPerson(Person person) {
        if (person == null) {
            throw new IllegalArgumentException();
        }
        if (!persons.contains(person)) {
            persons.add(person);
            person.addAddress(this);
        }
    }

    /**
     * Usuwa związek powiązania z obiektem reprezentującym osobę.
     * <p>
     * Wywołuje analogiczną metodę po stronie osoby.
     * </p>
     *
     * @param person Osoba, wobec której powiązanie ma zostać usunięte.
     */
    public void removePerson(Person person) {
        if (persons.contains(person)) {
            persons.remove(person);
            person.removeAddress(this);
        }
    }

    public String getCountry() {
        return country.toString();
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public HashSet<Person> getPersons() {
        return new HashSet<>(persons);
    }
}
