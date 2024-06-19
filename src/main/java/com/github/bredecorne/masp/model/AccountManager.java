package com.github.bredecorne.masp.model;

import java.util.HashSet;

public class AccountManager {
    
    // Atrybuty wymagane
    private String name;
    private Status status;
    
    // Ekstensja
    private static final HashSet<AccountManager> accountManagers = new HashSet<>();
    
    // Asocjacje wiele-do-wiele
    private final HashSet<PeriodEntrySet> periodEntrySets = new HashSet<>();


    public AccountManager(String name, Status status) {
        this.name = name;
        this.status = status;
        
        accountManagers.add(this); // Dodaje do ekstensji
    }
    
    
    public void addPeriodEntrySet(PeriodEntrySet periodEntrySet) {
        if (periodEntrySet == null) { throw new IllegalArgumentException(); }
        if (!periodEntrySets.contains(periodEntrySet)) {
            periodEntrySets.add(periodEntrySet);
            periodEntrySet.addAccountManager(this);
        }
    }
    
    public void removePeriodEntrySet(PeriodEntrySet periodEntrySet) {
        if (periodEntrySets.contains(periodEntrySet)) {
            periodEntrySets.remove(periodEntrySet);
            periodEntrySet.removeAccountManager(this);
        }
    }


    public static HashSet<AccountManager> getAccountManagers() {
        return new HashSet<>(accountManagers);
    }
    
    public PeriodEntrySet findPeriodEntrySet(String abbreviation) {
        for (PeriodEntrySet periodEntrySet : periodEntrySets) {
            if (periodEntrySet.getAbbreviation().equals(abbreviation)) {
                return periodEntrySet;
            }
        }
        return null;
    }
}
