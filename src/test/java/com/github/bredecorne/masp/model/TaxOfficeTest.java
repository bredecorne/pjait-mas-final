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
}