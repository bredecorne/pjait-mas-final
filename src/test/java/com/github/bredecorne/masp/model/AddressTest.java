package com.github.bredecorne.masp.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void createsAddressAndAssociatesItWithATaxOffice() {
        var country = Country.PL;
        var expectedCountry = "PL";
        var city = "Poznań";
        var street = "Łąkowa";
        var houseNumber = "4A";
        var apartmentNumber = "20";
        var taxOffice = new TaxOffice("Urząd Skarbowy nr 10 w Lipinkach-Łużyckich");
        
        var address = new Address(country, city, street, houseNumber, apartmentNumber);
        address.setTaxOffice(taxOffice);

        assertAll(
                () -> assertEquals(expectedCountry, address.getCountry()),
                () -> assertEquals(city, address.getCity()),
                () -> assertEquals(street, address.getStreet()),
                () -> assertEquals(houseNumber, address.getHouseNumber()),
                () -> assertEquals(apartmentNumber, address.getApartmentNumber()),
                () -> assertEquals(taxOffice, address.getTaxOffice()),
                () -> assertEquals(address, taxOffice.getAddresses().stream().findFirst().orElse(null))
        );
    }
}