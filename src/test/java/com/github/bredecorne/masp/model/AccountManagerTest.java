package com.github.bredecorne.masp.model;

import com.github.bredecorne.masp.model.persons.LegalPerson;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AccountManagerTest {

    @Test
    void createsAnAccountManagerObjectSuccessfully() {
        var name = "Katarzyna Kierownik";
        var status = Status.INACTIVE;
        
        var accountManager = new AccountManager(name, status);
        
        assertAll(
                () -> assertEquals(name, accountManager.getName()),
                () -> assertEquals(status, accountManager.getStatus())
        );
    }

    @Test
    void findsAnAssociatedPeriodEntrySetUsingAbbreviationSuccessfully() {
        var periodEntrySet1 = new PeriodEntrySet(
                LocalDate.now().minusDays(14), LocalDate.now(), new LegalPerson(
                        "Agata Nowak", Status.ACTIVE, BigDecimal.ZERO, 
                    false, BigDecimal.ZERO, false
        ));
        var periodEntrySet2 = new PeriodEntrySet(
                LocalDate.now().minusDays(14), LocalDate.now(), new LegalPerson(
                "Maciej Kowalski", Status.ACTIVE, BigDecimal.ZERO,
                false, BigDecimal.ZERO, false
        ));
        var accountManager = new AccountManager("Kamil Krzysztof", Status.ACTIVE);
        
        // Tworzenie powiązań
        accountManager.addPeriodEntrySet(periodEntrySet1);
        accountManager.addPeriodEntrySet(periodEntrySet2);
        
        // Znajdowanie period entry set na podstawie kwalifikatora
        var found = accountManager.findPeriodEntrySet(periodEntrySet1.getAbbreviation());
        
        assertEquals(periodEntrySet1, found);
    }
}