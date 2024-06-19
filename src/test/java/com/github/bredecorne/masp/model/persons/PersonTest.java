package com.github.bredecorne.masp.model.persons;

import com.github.bredecorne.masp.model.Status;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
class PersonTest {
    
    @Test
    void createsAPersonThatIsPreferentialAndSetsItsDiscountRateAttributeSuccessfully() {
        var name = "Grzegorz Donald i wspólnicy sp.j.";
        var status = Status.ACTIVE;
        var feeRate = new BigDecimal(5.5);
        var preferential = true;
        var discountRate = new BigDecimal(2);
        var uniformTax = false;
        
        var person = new NaturalPerson(name, status, feeRate, preferential, discountRate, uniformTax);
        
        assertAll(
                () -> assertEquals(name, person.getName()),
                () -> assertEquals(status, person.getStatus()),
                () -> assertEquals(feeRate, person.getFeeRate()),
                () -> assertEquals(preferential, person.isPreferential()),
                () -> assertEquals(discountRate, person.getDiscountRate()),
                () -> assertEquals(uniformTax, person.isUniformTax())
        );
    }

    @Test
    void changesAPreferentialPersonToANonPreferentialAndAssignsLoyaltyPointsAttribute() {
        var name = "Grzegorz Donald i wspólnicy sp.j.";
        var status = Status.ACTIVE;
        var feeRate = new BigDecimal("5.5");
        var preferential = false;
        var discountRate = new BigDecimal(2);
        var uniformTax = false;
        var person = new NaturalPerson(name, status, feeRate, preferential, discountRate, uniformTax);
        var loyaltyPoints = BigDecimal.ZERO;
        
        person.setPreferential(preferential, loyaltyPoints);
        
        assertAll(
                () -> assertEquals(preferential, person.isPreferential()),
                () -> assertEquals(loyaltyPoints, person.getLoyaltyPoints()),
                () -> assertThrows(RuntimeException.class, person::getDiscountRate)
        );
    }
}