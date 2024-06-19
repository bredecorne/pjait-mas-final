package com.github.bredecorne.masp.model.persons;

import com.github.bredecorne.masp.model.Status;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    void changesAPreferentialPersonToANonPreferentialAssignsLoyaltyPointsAttributeSuccessfully() {
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

    @Test
    void changesAPreferentialPersonToANonPreferentialAndGetsExceptionWhenInvokingGetDiscountRate() {
        var name = "Grzegorz Donald i wspólnicy sp.j.";
        var status = Status.ACTIVE;
        var feeRate = new BigDecimal("5.5");
        var preferential = false;
        var discountRate = new BigDecimal(2);
        var uniformTax = false;
        var person = new NaturalPerson(name, status, feeRate, preferential, discountRate, uniformTax);
        var loyaltyPoints = BigDecimal.ZERO;

        person.setPreferential(preferential, loyaltyPoints);

        assertThrows(RuntimeException.class, person::getDiscountRate);
    }

    @Test
    void accessesExtensionSuccessfully() {
        var person1 = new NaturalPerson("Agata Skowronek", Status.INACTIVE, new BigDecimal("4.4"),
                false, BigDecimal.ZERO, false);
        var person2 = new LegalPerson("Krzak Ogrodnictwo", Status.ACTIVE, new BigDecimal("9.1"),
                false, BigDecimal.ZERO, false);
        var person3 = new LegalPerson("Krzak Holdings", Status.ACTIVE, new BigDecimal("11.1"),
                false, BigDecimal.ZERO, true);
        var persons = new HashSet<Person>();
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);
        
        assertEquals(persons, Person.getPersons());
    }
}