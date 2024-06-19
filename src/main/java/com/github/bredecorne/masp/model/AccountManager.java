package com.github.bredecorne.masp.model;

import java.io.Serializable;
import java.util.HashSet;

public class AccountManager implements Serializable {

    // Ekstensja
    private static HashSet<AccountManager> accountManagers = new HashSet<>();
    // Asocjacje wiele-do-wiele
    private final HashSet<PeriodEntrySet> periodEntrySets = new HashSet<>();
    // Atrybuty wymagane
    private final String name;
    private final Status status;


    public AccountManager(String name, Status status) {
        this.name = name;
        this.status = status;

        accountManagers.add(this); // Dodaje do ekstensji
    }

    public static HashSet<AccountManager> getAccountManagers() {
        return new HashSet<>(accountManagers);
    }

    public static void setAccountManagers(HashSet<AccountManager> accountManagers) {
        AccountManager.accountManagers = accountManagers;
    }

    public void addPeriodEntrySet(PeriodEntrySet periodEntrySet) {
        if (periodEntrySet == null) {
            throw new IllegalArgumentException();
        }
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

    public PeriodEntrySet findPeriodEntrySet(String abbreviation) {
        for (PeriodEntrySet periodEntrySet : periodEntrySets) {
            if (periodEntrySet.getAbbreviation().equals(abbreviation)) {
                return periodEntrySet;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }
}
