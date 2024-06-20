package com.github.bredecorne.masp.model.persons;

import com.github.bredecorne.masp.model.PeriodEntrySet;
import com.github.bredecorne.masp.model.Status;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

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
        Person.setPersons(new HashSet<>());
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

    @Test
    void createsAPersonThatIsAssociatedWithAPeriodEntrySetAndChangesTheAssociationToANewPeriodEntrySetSuccessfully() {
        var person1 = new LegalPerson("Agata S.A.", Status.ACTIVE, BigDecimal.ZERO, false,
                BigDecimal.ZERO, false);
        var person2 = new NaturalPerson("Monika Bogdan", Status.ACTIVE, BigDecimal.TEN, false,
                BigDecimal.ZERO, false);
        var periodEntrySet1 = new PeriodEntrySet(LocalDate.now(), LocalDate.now(), person1);
        periodEntrySet1.addEntry(LocalDate.now(), BigDecimal.ONE);
        periodEntrySet1.addEntry(LocalDate.now(), BigDecimal.ONE);
        var periodEntrySet2 = new PeriodEntrySet(LocalDate.now().minusDays(10000), LocalDate.now(), person2);
        periodEntrySet2.addEntry(LocalDate.now(), BigDecimal.ONE);
        periodEntrySet2.addEntry(LocalDate.now(), BigDecimal.ONE);

        person2.replacePeriodEntrySet(periodEntrySet2, person1); // Zmienia powiązanie

        assertAll(
                () -> assertTrue(person1.getPeriodEntrySets().contains(periodEntrySet2)),
                () -> assertFalse(person2.getPeriodEntrySets().contains(periodEntrySet2))
        );
    }
}