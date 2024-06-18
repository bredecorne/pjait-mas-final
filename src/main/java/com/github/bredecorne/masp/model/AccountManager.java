package com.github.bredecorne.masp.model;

import java.util.HashSet;

public class AccountManager {
    
    // Atrybuty wymagane
    private String name;
    private Status status;
    
    // Ekstensja
    private final HashSet<AccountManager> accountManagers = new HashSet<>();
    
    // Asocjacje wiele-do-wiele
    private final HashSet<PeriodEntrySet> periodEntrySets = new HashSet<>();
    
}
