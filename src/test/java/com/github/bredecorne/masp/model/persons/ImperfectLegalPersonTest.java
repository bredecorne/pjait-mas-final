package com.github.bredecorne.masp.model.persons;

import com.github.bredecorne.masp.model.Status;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ImperfectLegalPersonTest {

    @Test
    void createsObjectSuccessfully() {
        var name = "Gmina Hrubieszów";
        var status = Status.ACTIVE;
        var feeRate = BigDecimal.TEN;
        var preferential = false;
        var preferentialAttribute = BigDecimal.ZERO;
        
        var imperfectLegalPerson = new ImperfectLegalPerson(name, status, feeRate, 
                preferential, preferentialAttribute); 
        
        assertAll(
                () -> assertEquals(name, imperfectLegalPerson.getName()),
                () -> assertEquals(status, imperfectLegalPerson.getStatus()),
                () -> assertEquals(0, feeRate.compareTo(imperfectLegalPerson.getFeeRate())),
                () -> assertEquals(preferential, imperfectLegalPerson.isPreferential()),
                () -> assertEquals(0, preferentialAttribute.compareTo(imperfectLegalPerson.getLoyaltyPoints()))
        );
    }
}