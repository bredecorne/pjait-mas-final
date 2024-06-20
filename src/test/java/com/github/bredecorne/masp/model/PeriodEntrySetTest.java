package com.github.bredecorne.masp.model;

import com.github.bredecorne.masp.model.persons.LegalPerson;
import com.github.bredecorne.masp.model.persons.NaturalPerson;
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

}