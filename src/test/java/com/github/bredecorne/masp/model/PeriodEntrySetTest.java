package com.github.bredecorne.masp.model;

import com.github.bredecorne.masp.model.persons.LegalPerson;
import com.github.bredecorne.masp.model.persons.NaturalPerson;
import com.github.bredecorne.masp.model.taxes.UniformTax;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PeriodEntrySetTest {

    @Test
    void createsAPersonThatIsAssociatedWithAPeriodEntrySetAndChangesTheAssociationToANewPersonSuccessfully() {
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

        periodEntrySet2.setPerson(person1); // Zmienia powiązanie

        assertAll(
                () -> assertTrue(person1.getPeriodEntrySets().contains(periodEntrySet2)),
                () -> assertFalse(person2.getPeriodEntrySets().contains(periodEntrySet2))
        );
    }

    @Test
    void calculatesNumericalValuesCorrectly() {
        var person = new LegalPerson("Agata S.A.", Status.ACTIVE, BigDecimal.ZERO, false,
                BigDecimal.ZERO, false);
        var periodEntrySet = new PeriodEntrySet(LocalDate.now(), LocalDate.now(), person);
        var taxValue = new UniformTax(new BigDecimal("0.1"));
        periodEntrySet.addEntry(LocalDate.now(), new BigDecimal("1000"));
        periodEntrySet.addEntry(LocalDate.now(), new BigDecimal("1000"));
        periodEntrySet.addEntry(LocalDate.now(), new BigDecimal("-500"));
        periodEntrySet.addEntry(LocalDate.now(), new BigDecimal("-500"));
        var expectedRevenue = new BigDecimal("2000");
        var expectedExpenses = new BigDecimal("-1000");
        var expectedIncome = new BigDecimal("1000");
        var expectedTax = new BigDecimal("100");
        var expectedAfterTaxIncome = new BigDecimal("900"); 
        
        var revenue = periodEntrySet.getRevenue();
        var expenses = periodEntrySet.getExpenses();
        var income = periodEntrySet.getIncome();
        var tax = periodEntrySet.getTax(taxValue);
        var afterTaxIncome = periodEntrySet.getAfterTaxIncome(taxValue);
        
        assertAll(
                () -> assertEquals(0, expectedRevenue.compareTo(revenue)),
                () -> assertEquals(0, expectedExpenses.compareTo(expenses)),
                () -> assertEquals(0, expectedIncome.compareTo(income)),
                () -> assertEquals(0, expectedTax.compareTo(tax)),
                () -> assertEquals(0, expectedAfterTaxIncome.compareTo(afterTaxIncome))
        );
    }
}