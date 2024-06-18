package com.github.bredecorne.masp.model;

import com.github.bredecorne.masp.model.persons.Person;

import java.util.HashSet;
import java.util.regex.Pattern;

public class Address {
    
    // Atrybuty wymagane
    private Country country;
    private String street;
    private String houseNumber;
    private String apartmentNumber;
    
    // Ekstensja
    private static final HashSet<Address> addresses = new HashSet<>();

    // Asocjacje wiele-do-wiele
    private final HashSet<Person> persons = new HashSet<>();
    
    // Asocjacje jeden-do-wiele (po stronie wiele)
    private final HashSet<TaxOffice> taxOffices = new HashSet<>();

    
    public Address(Country country, String street, String houseNumber, String apartmentNumber) {
        if (country == null || street == null || validateHouseOrApartmentNumber(houseNumber) ||
                validateHouseOrApartmentNumber(apartmentNumber)) { throw new IllegalArgumentException(); }
        
        this.country = country;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
        
        addresses.add(this);
    }
    
    public void addPerson(Person person) {
        if (!persons.contains(person)) {
            persons.add(person);
        }
    }
    
    public void addTaxOffice(TaxOffice taxOffice) {
        if (!taxOffices.contains(taxOffice)) {
            taxOffice.setAddress(this);
            if (taxOffice.getAddress() == this) {
                taxOffices.add(taxOffice);
            }
        }
    }
    
    public void removePerson(Person person) {
        if (persons.contains(person)) {
            persons.remove(person);
        }
    }
    
    public void removeTaxOffice(TaxOffice taxOffice) {
        if (taxOffices.contains(taxOffice)) {
            taxOffices.remove(taxOffice);
        }
    }
    
    private static boolean validateHouseOrApartmentNumber(String number) {
        var pattern = Pattern.compile("^(\\d+)([A-Z]{0,2})([/-]\\d+)?([A-Z]{0,2})?$");
        return !pattern.matcher(number).matches();
    }
    
}
