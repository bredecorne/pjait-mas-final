package com.github.bredecorne.masp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaxOfficeTest {
    
    @Test
    void createsTaxOfficeWithoutProvidingAnAddress() {
        var name = "Urząd Skarbowy nr 10 w Lipinkach-Łużyckich";
        
        var taxOffice = new TaxOffice(name);
        
        assertEquals(name, taxOffice.getName());
    }

    @Test
    void createsTaxOfficesAndAssociatesItWithAnAddress() {
        var name = "Urząd Skarbowy nr 10 w Lipinkach-Łużyckich";
        var address = new Address(Country.PL, "Łąkowa", "4A", "20");
        
        var taxOffice = new TaxOffice(name, address);
        
        assertAll(
                () -> assertEquals(name, taxOffice.getName()),
                () -> assertEquals(address, taxOffice.getAddress())
        );
    }

    @Test
    void modifiesTaxOfficeAssociationWithAnAddress() {
        var name = "Urząd Skarbowy nr 10 w Lipinkach-Łużyckich";
        var address = new Address(Country.PL, "Łąkowa", "4A", "20");
        var address1 = new Address(Country.PL, "Betonowa", "70B", "1");

        var taxOffice = new TaxOffice(name, address);
        taxOffice.setAddress(address1);

        assertAll(
                () -> assertEquals(name, taxOffice.getName()),
                () -> assertEquals(address1, taxOffice.getAddress())
        );
    }
}