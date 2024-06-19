package com.github.bredecorne.masp.model.persons;

import com.github.bredecorne.masp.model.Address;
import com.github.bredecorne.masp.model.Status;

import java.math.BigDecimal;
import java.util.HashSet;

public abstract class Person {
    
    // Atrybuty wymagane
    private String name;
    private Status status;
    private BigDecimal feeRate;
    private boolean preferential;
    
    // Atrybuty opcjonalne
    private BigDecimal loyaltyPoints; // Może być użyty przez obiekt, dla którego preferential przyjmuje wartość true
    private BigDecimal discountRate; // Może być użyty przez obiekt, dla którego preferential przyjmuje wartość false
    
    // Atrybuty klasowe
    private static BigDecimal PREFERENTIAL_THRESHOLD = BigDecimal.valueOf(10000);
    
    // Ekstensja
    private static final HashSet<Person> persons = new HashSet<>();
    
    // Asocjacje wiele-do-wiele
    private final HashSet<Address> addresses = new HashSet<>();


    /**
     * Konstruktor dla obiektu typu Person.
     * @param name Nazwa danej osoby.
     * @param status Aktualny status osoby.
     * @param feeRate Wartość prowizji.
     * @param preferential Status klienta.
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
    
    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if (status == null) { throw new IllegalArgumentException(); }
        this.status = status;
    }

    public void setFeeRate(BigDecimal feeRate) {
        if (feeRate.compareTo(BigDecimal.ZERO) < 0) { throw new IllegalArgumentException(); }
        this.feeRate = feeRate;
    }

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public boolean isPreferential() {
        return preferential;
    }

    /**
     * Zmienia typ klienta.
     * @param preferential          Nowy typ klienta.
     * @param preferentialAttribute Wartość zależna od statusu klienta – dla klienta o statusie preferential == true
     *                              określa atrybut reprezentujący nadaną zniżkę; w przeciwnym razie określa atrybut
     *                              reprezentujący liczbę zebranych punktów lojalnościowych.
     */
    public void setPreferential(boolean preferential, BigDecimal preferentialAttribute) {
        if (preferentialAttribute.compareTo(BigDecimal.ZERO) < 0) { throw new IllegalArgumentException(); }
        
        this.preferential = preferential;
        
        if (preferential) {
            this.discountRate = preferentialAttribute;
        } else {
            this.loyaltyPoints = preferentialAttribute;
        }
    }

    public BigDecimal getLoyaltyPoints() {
        if (preferential) throw new RuntimeException();
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(BigDecimal loyaltyPoints) {
        if (preferential) throw new RuntimeException();
        this.loyaltyPoints = loyaltyPoints;
    }

    public BigDecimal getDiscountRate() {
        if (!preferential) throw new RuntimeException();
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        if (!preferential) throw new RuntimeException();
        this.discountRate = discountRate;
    }

    public static BigDecimal getPreferentialThreshold() {
        return PREFERENTIAL_THRESHOLD;
    }

    public static void setPreferentialThreshold(BigDecimal preferentialThreshold) {
        if (preferentialThreshold.compareTo(BigDecimal.ZERO) < 0) { throw new IllegalArgumentException(); }
        PREFERENTIAL_THRESHOLD = preferentialThreshold;
    }

    /**
     * Tworzy związek powiązania z obiektem reprezentującym adres.
     * <p>
     *     Wywołuje analogiczną metodę po stronie adresu.
     * </p>
     * @param address Adres, wartość niebędąca null.
     */
    public void addAddress(Address address) {
        if (address == null) { throw new IllegalArgumentException(); }
        if (!addresses.contains(address)) {
            addresses.add(address);
            address.addPerson(this);
        }
    }

    /**
     * Usuwa związek powiązania z obiektem reprezentującym adres.
     * <p>
     *     Wywołuje analogiczną metodę po stronie adresu.
     * </p>
     * @param address Adres, wobec którego powiązanie ma zostać usunięte.
     */
    public void removeAddress(Address address) {
        if (addresses.contains(address)) {
            addresses.remove(address);
            address.removePerson(this);
        }
    }

    public HashSet<Address> getAddresses() {
        return new HashSet<>(addresses);
    }
}
