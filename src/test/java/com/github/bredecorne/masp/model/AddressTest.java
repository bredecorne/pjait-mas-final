package com.github.bredecorne.masp.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void createsAddressAndAssociatesItWithATaxOffice() {
        var country = Country.PL;
        var street = "Łąkowa";
        var houseNumber = "4A";
        var apartmentNumber = "20";
        var taxOffice = new TaxOffice("Urząd Skarbowy nr 10 w Lipinkach-Łużyckich");
        var taxOffices = new HashSet<TaxOffice>();
        taxOffices.add(taxOffice);
        
        var address = new Address(country, street, houseNumber, apartmentNumber);
        address.addTaxOffice(taxOffice);

        assertAll(
                () -> assertEquals(country, address.getCountry()),
                () -> assertEquals(street, address.getStreet()),
                () -> assertEquals(houseNumber, address.getHouseNumber()),
                () -> assertEquals(apartmentNumber, address.getApartmentNumber()),
                () -> assertEquals(taxOffices, address.getTaxOffices()),
                () -> assertEquals(address, taxOffice.getAddress())
        );
    }

    @Test
    void modifiesAddressAssociationWithATaxOffice() {
        var country = Country.PL;
        var street = "Łąkowa";
        var houseNumber = "4A";
        var apartmentNumber = "20";
        var taxOffice = new TaxOffice("Urząd Skarbowy nr 10 w Lipinkach-Łużyckich");
        var taxOffices = new HashSet<TaxOffice>();

        var address = new Address(country, street, houseNumber, apartmentNumber);
        address.addTaxOffice(taxOffice);
        address.removeTaxOffice(taxOffice);

        assertAll(
                () -> assertEquals(country, address.getCountry()),
                () -> assertEquals(street, address.getStreet()),
                () -> assertEquals(houseNumber, address.getHouseNumber()),
                () -> assertEquals(apartmentNumber, address.getApartmentNumber()),
                () -> assertEquals(taxOffices, address.getTaxOffices()),
                () -> assertNull(taxOffice.getAddress())
        );
    }
    
}