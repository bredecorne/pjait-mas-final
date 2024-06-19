package com.github.bredecorne.masp.utils;

import com.github.bredecorne.masp.model.*;
import com.github.bredecorne.masp.model.persons.LegalPerson;
import com.github.bredecorne.masp.model.persons.Person;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    @Test
    void serializesAndDeserializesSuccessfully() {
        var accountManager = new AccountManager("John Doe", Status.ACTIVE);
        var address = new Address(Country.PL, "Poznań", "Warszawska", "12", "3A");
        var person = new LegalPerson("Company XYZ", Status.ACTIVE, BigDecimal.TEN, false, 
                BigDecimal.ZERO, true);
        var periodEntrySet = new PeriodEntrySet(LocalDate.now(), LocalDate.now().plusDays(30), person);
        periodEntrySet.addEntry(LocalDate.now(), BigDecimal.TEN);
        periodEntrySet.addEntry(LocalDate.now().minusDays(1), BigDecimal.TWO);
        var taxOffice = new TaxOffice("Urząd m.st. Częstochowy");
        
        Repository.serialize();
        
        AccountManager.setAccountManagers(new HashSet<>());
        Address.setAddresses(new HashSet<>());
        Person.setPersons(new HashSet<>());
        PeriodEntrySet.setPeriodEntrySets(new HashSet<>());
        TaxOffice.setTaxOffices(new HashSet<>());
        
        Repository.deserialize();
        
        assertAll(
                () -> assertEquals(accountManager.getName(), 
                        AccountManager.getAccountManagers().stream().toList().getFirst().getName()),
                () -> assertEquals(address.getStreet(), 
                        Address.getAddresses().stream().toList().getFirst().getStreet()),
                () -> assertEquals(person.getName(), 
                        Person.getPersons().stream().toList().getFirst().getName()),
                () -> assertEquals(periodEntrySet.getAbbreviation(), 
                        PeriodEntrySet.getPeriodEntrySets().stream().toList().getFirst().getAbbreviation()),
                () -> assertEquals(taxOffice.getName(), 
                        TaxOffice.getTaxOffices().stream().toList().getFirst().getName())
        );
    }
}