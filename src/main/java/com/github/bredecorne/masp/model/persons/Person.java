package com.github.bredecorne.masp.model.persons;

import com.github.bredecorne.masp.model.Address;
import com.github.bredecorne.masp.model.PeriodEntrySet;
import com.github.bredecorne.masp.model.Status;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;


/**
 * Klasa abstrakcyjna reprezentująca osobę/klienta.
 */
public abstract class Person implements Serializable {

    // Atrybuty klasowe
    private static BigDecimal PREFERENTIAL_THRESHOLD = BigDecimal.valueOf(10000);
    
    // Ekstensja
    private static HashSet<Person> persons = new HashSet<>();
    
    // Asocjacje wiele-do-wiele
    private final HashSet<Address> addresses = new HashSet<>();
    private final HashSet<PeriodEntrySet> periodEntrySets = new HashSet<>();
    
    // Atrybuty wymagane
    private final String name;
    private Status status;
    private BigDecimal feeRate;
    private boolean preferential;
    
    // Atrybuty opcjonalne
    private BigDecimal loyaltyPoints; // Może być użyty przez obiekt, dla którego preferential przyjmuje wartość true
    private BigDecimal discountRate; // Może być użyty przez obiekt, dla którego preferential przyjmuje wartość false


    /**
     * Konstruktor dla obiektu typu Person.
     *
     * @param name                  Nazwa danej osoby.
     * @param status                Aktualny status osoby.
     * @param feeRate               Wartość prowizji.
     * @param preferential          Status klienta.
     * @param preferentialAttribute Wartość zależna od statusu klienta – dla klienta o statusie preferential == true
     *                              określa atrybut reprezentujący nadaną zniżkę; w przeciwnym razie określa atrybut
     *                              reprezentujący liczbę zebranych punktów lojalnościowych.
     */
    public Person(String name, Status status, BigDecimal feeRate, boolean preferential,
                  BigDecimal preferentialAttribute) {
        this.name = name;
        this.status = status;
        this.feeRate = feeRate;
        this.preferential = preferential;

        if (preferential && preferentialAttribute.compareTo(BigDecimal.ZERO) > 0) {
            this.discountRate = preferentialAttribute;
        } else {
            this.loyaltyPoints = preferentialAttribute;
        }

        persons.add(this); // Dodaje do ekstensji
    }

    public static BigDecimal getPreferentialThreshold() {
        return PREFERENTIAL_THRESHOLD;
    }


    /**
     * Ustawia wartość progu, po którego przekroczeniu klient może być traktowany preferencyjnie.
     * Weryfikuje czy podawany próg jest wyższy od wartości 0; w przeciwnym razie zwraca wyjątek.
     * @param preferentialThreshold Wartość progu, po którego przekroczeniu klient może być traktowany preferencyjnie.
     */
    public static void setPreferentialThreshold(BigDecimal preferentialThreshold) {
        if (preferentialThreshold.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        PREFERENTIAL_THRESHOLD = preferentialThreshold;
    }

    
    /**
     * Zwraca kopię ekstensji.
     * @return Kopia ekstensji.
     */
    public static HashSet<Person> getPersons() {
        return new HashSet<>(persons);
    }

    
    public static void setPersons(HashSet<Person> persons) {
        Person.persons = persons;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }


    /**
     * Ustawia status osoby.
     * Weryfikuje, czy podawany status nie jest wartością null; w przeciwnym razie zwraca wyjątek.
     * @param status Obiekt klasy wyliczeniowej Status.
     */
    public void setStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException();
        }
        this.status = status;
    }

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    
    /**
     * Ustawia prowizję.
     * Weryfikuje czy podawana prowizja jest wyższa od wartości 0; w przeciwnym razie zwraca wyjątek.
     * @param feeRate Wartość prowizji.
     */
    public void setFeeRate(BigDecimal feeRate) {
        if (feeRate.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        this.feeRate = feeRate;
    }

    public boolean isPreferential() {
        return preferential;
    }

    /**
     * Zmienia typ klienta.
     *
     * @param preferential          Nowy typ klienta.
     * @param preferentialAttribute Wartość zależna od statusu klienta – dla klienta o statusie preferential == true
     *                              określa atrybut reprezentujący nadaną zniżkę; w przeciwnym razie określa atrybut
     *                              reprezentujący liczbę zebranych punktów lojalnościowych.
     */
    public void setPreferential(boolean preferential, BigDecimal preferentialAttribute) {
        if (preferentialAttribute.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }

        this.preferential = preferential;

        if (preferential) {
            this.discountRate = preferentialAttribute;
        } else {
            this.loyaltyPoints = preferentialAttribute;
        }
    }


    /**
     * Zwraca wartość zmiennej punktów lojalnościowych.
     * Ma zastosowanie wyłącznie wobec klientów, którzy nie są klientami preferencyjnymi. W razie wywołania dla
     * klientów preferencyjnych zwraca wyjątek.
     * @return Liczba punktów lojalnościowych klienta
     */
    public BigDecimal getLoyaltyPoints() {
        if (preferential) throw new RuntimeException();
        return loyaltyPoints;
    }


    /**
     * Ustawia wartość zmiennej punktów lojalnościowych.
     * Ma zastosowanie wyłącznie wobec klientów, którzy nie są klientami preferencyjnymi. W razie wywołania dla
     * klientów preferencyjnych zwraca wyjątek.
     * @param loyaltyPoints Oczekiwana liczba punktów lojalnościowych klienta.
     */
    public void setLoyaltyPoints(BigDecimal loyaltyPoints) {
        if (preferential) throw new RuntimeException();
        this.loyaltyPoints = loyaltyPoints;
    }


    /**
     * Zwraca wartość zniżki dla klientów preferencyjnych.
     * Ma zastosowanie wyłącznie wobec klientów, którzy są klientami preferencyjnymi. W razie wywołania dla
     * klientów niepreferencyjnych zwraca wyjątek.
     * @return Wartość zniżki stosowanej dla klienta preferencyjnego.
     */
    public BigDecimal getDiscountRate() {
        if (!preferential) throw new RuntimeException();
        return discountRate;
    }


    /**
     * Ustawia wartość zniżki dla klientów preferencyjnych.
     * Ma zastosowanie wyłącznie wobec klientów, którzy są klientami preferencyjnymi. W razie wywołania dla
     * klientów niepreferencyjnych zwraca wyjątek.
     * @param discountRate Oczekiwana wartość zniżki stosowanej dla klienta preferencyjnego.
     */
    public void setDiscountRate(BigDecimal discountRate) {
        if (!preferential) throw new RuntimeException();
        this.discountRate = discountRate;
    }

    
    /**
     * Tworzy związek powiązania z obiektem reprezentującym adres.
     * <p>
     * Wywołuje analogiczną metodę po stronie adresu.
     * </p>
     *
     * @param address Adres, wartość niebędąca null.
     */
    public void addAddress(Address address) {
        if (address == null) {
            throw new IllegalArgumentException();
        }
        if (!addresses.contains(address)) {
            addresses.add(address);
            address.addPerson(this);
        }
    }

    
    /**
     * Usuwa związek powiązania z obiektem reprezentującym adres.
     * <p>
     * Wywołuje analogiczną metodę po stronie adresu.
     * </p>
     *
     * @param address Adres, wobec którego powiązanie ma zostać usunięte.
     */
    public void removeAddress(Address address) {
        if (addresses.contains(address)) {
            addresses.remove(address);
            address.removePerson(this);
        }
    }

    
    /**
     * Zwraca kopię powiązań (liczność wiele-do-wiele) z obiektami adresu (zamieszkania lub siedziby klienta).
     * @return Kopia zbioru powiązań z obiektami reprezentującymi adres.
     */
    public HashSet<Address> getAddresses() {
        return new HashSet<>(addresses);
    }


    /**
     * Dodaje nowe powiązanie ze zbiorem wartości księgowych.
     * Zwraca wyjątek w sytuacji, w której zbiór wartości księgowych jest null.
     * Wywołuje analogiczną metodę w obiekcie zbioru wartości księgowych (po stronie jeden).
     * @param periodEntrySet Zbiór wartości księgowych, niebędący null.
     */
    public void addPeriodEntrySet(PeriodEntrySet periodEntrySet) {
        if (periodEntrySet == null) { throw new IllegalArgumentException(); }
        if (!periodEntrySets.contains(periodEntrySet)) {
            periodEntrySets.add(periodEntrySet);
            periodEntrySet.setPerson(this);
        }
    }


    /**
     * Usuwa zbiór wartości księgowych z właściwości danego klienta. Przypisuje zbiorowi nową właściwość klienta.
     * Zwraca wyjątek w sytuacji, w której zbiór wartości księgowych lub klient jest null.
     * Wywołuje analogiczną metodę w obiekcie zbioru wartości księgowych (po stronie jeden).
     * @param periodEntrySet Zbiór wartości księgowych, niebędący null.
     * @param newPerson Klient, któremu nadana zostanie właściwość dla danego zbioru księgowego.
     */
    public void replacePeriodEntrySet(PeriodEntrySet periodEntrySet, Person newPerson) {
        if (periodEntrySet == null || newPerson == null) { throw new IllegalArgumentException(); }
        if (periodEntrySets.contains(periodEntrySet)) {
            periodEntrySets.remove(periodEntrySet);
            periodEntrySet.setPerson(newPerson);
            newPerson.addPeriodEntrySet(periodEntrySet);
        }
    }


    /**
     * Zwraca kopię powiązań ze zbiorami wpisów księgowych.
     * @return Kopia powiązań ze zbiorami wpisów księgowych.
     */
    public HashSet<PeriodEntrySet> getPeriodEntrySets() {
        return new HashSet<>(periodEntrySets);
    }
}
