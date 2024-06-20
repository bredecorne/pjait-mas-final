package com.github.bredecorne.masp.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

public class AccountManager implements Serializable {

    
    // Ekstensja
    private static HashSet<AccountManager> accountManagers = new HashSet<>();

    // Asocjacje kwalifikowana
    private final HashMap<String, PeriodEntrySet> periodEntrySets = new HashMap<>();

    // Atrybuty wymagane
    private final String name;
    private final Status status;


    public AccountManager(String name, Status status) {
        this.name = name;
        this.status = status;

        accountManagers.add(this); // Dodaje do ekstensji
    }
    
    
    /**
     * Zwraca kopię ekstensji klasy.
     * @return Kopia ekstensji klasy.
     */
    public static HashSet<AccountManager> getAccountManagers() {
        return new HashSet<>(accountManagers);
    }


    /**
     * Ustawia kopię ekstensji klasy – wymagane przez mechanizm serializacji.
     * @param accountManagers Ekstensja klasy.
     */
    public static void setAccountManagers(HashSet<AccountManager> accountManagers) {
        AccountManager.accountManagers = accountManagers;
    }


    /**
     * Dodaje nowe powiązanie z obiektem zbioru wpisów księgowych.
     * Korzysta z abbreviation jako klucza.
     *
     * @param periodEntrySet Zbiór wpisów księgowych, niebędący null.
     */
    public void addPeriodEntrySet(PeriodEntrySet periodEntrySet) {
        if (periodEntrySet == null) {
            throw new IllegalArgumentException();
        }
        if (!periodEntrySets.containsKey(periodEntrySet.getAbbreviation())) {
            periodEntrySets.put(periodEntrySet.getAbbreviation(),
                    periodEntrySet);
            periodEntrySet.setAccountManager(this);
        }
    }


    /**
     * Usuwa powiązanie z obiektem zbioru wpisów księgowych.
     *
     * @param periodEntrySet Zbiór wpisów księgowych, dla którego istnieje już powiązanie.
     */
    public void removePeriodEntrySet(PeriodEntrySet periodEntrySet) {
        if (periodEntrySets.containsKey(periodEntrySet.getAbbreviation())) {
            periodEntrySets.remove(periodEntrySet.getAbbreviation());
            periodEntrySet.setAccountManager(this);
        }
    }


    /**
     * Zwraca powiązany zbiór wpisów księgowych na podstawie jego abbreviation (kwalifikatora).
     *
     * @param abbreviation Atrybut pochodny abbreviation w klasie zbioru wpisów księgowych, wywoływany przez
     *                     getAbbreviation()
     * @return Powiązany zbiór wpisów księgowych na podstawie określonego kwalifikatora.
     */
    public PeriodEntrySet findPeriodEntrySet(String abbreviation) {
        return periodEntrySets.get(abbreviation);
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }
}
