package com.github.bredecorne.masp.model;

import com.github.bredecorne.masp.model.persons.Person;
import com.github.bredecorne.masp.model.taxes.Tax;

import java.awt.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashSet;


/**
 * Klasa reprezentująca zbiór wpisów księgowych dla danego okresu i osoby.
 * <p>
 * Zawiera informacje o datach początkowej i końcowej, osobie, której dotyczy zbiór,
 * oraz wpisach księgowych (Entry). Umożliwia zarządzanie powiązaniami z menedżerami kont i podatkami.
 * </p>
 */
public class PeriodEntrySet implements Serializable {

    // Ekstensja
    private static HashSet<PeriodEntrySet> periodEntrySets = new HashSet<>();

    // Atrybuty wymagane
    private final HashSet<Entry> entries = new HashSet<>();
    private final LocalDate dateFrom;
    private final LocalDate dateTo;

    // Asocjacje wiele-do-wiele
    private final HashSet<Tax> taxes = new HashSet<>();
    
    // Asocjacja kwalifikowana
    private AccountManager accountManager;

    // Asocjacje jeden-do-wiele (po stronie jeden)
    private Person person;


    /**
     * Konstruktor tworzący nowy obiekt PeriodEntrySet.
     *
     * @param dateFrom Data początkowa okresu, którego wpisy będą zawierały się w danym zbiorze.
     * @param dateTo   Data końcowa okresu, którego wpisy będą zawierały się w danym zbiorze.
     * @param person   Osoba – klient, który jest właścicielem zbioru wpisów księgowych.
     */
    public PeriodEntrySet(LocalDate dateFrom, LocalDate dateTo, Person person) {
        if (dateFrom == null || dateTo == null ||
                person == null) {
            throw new IllegalArgumentException();
        }
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.person = person;
        
        person.addPeriodEntrySet(this); // Dodaje powiązanie z obiektem osoby
        periodEntrySets.add(this); // Dodaje do ekstensji
    }

    public static HashSet<PeriodEntrySet> getPeriodEntrySets() {
        return new HashSet<>(periodEntrySets);
    }

    public static void setPeriodEntrySets(HashSet<PeriodEntrySet> periodEntrySets) {
        PeriodEntrySet.periodEntrySets = periodEntrySets;
    }

    public HashSet<Entry> getEntries() {
        return new HashSet<>(entries);
    }

    public Person getPerson() {
        return person;
    }


    /**
     * Ustawia osobę/klienta, który jest właścicielem danego zbioru wartości księgowych.
     * Zwraca wyjątek w sytuacji, gdy klient jest null.
     * Wywołuje analogiczną metodę w obiekcie osoby (po stronie wiele).
     * @param person Osoba, niebędąca null.
     */
    public void setPerson(Person person) {
        if (person == null) { throw new IllegalArgumentException(); }
        if (person != this.person) {
            this.person.replacePeriodEntrySet(this, person);
            this.person = person;
        }
    }

    /**
     * Określa wartość atrybutu pochodnego abbreviation, który stanowi podstawę asocjacji kwalifikowanej
     * z klasą reprezentującą menedżera konta.
     * W określaniu wartości uwzględnia zakres dat oraz nazwę klienta.
     *
     * @return abbreviation Ciąg znaków złożony z kolejno: nazwy klienta, daty początkowej zbioru wpisów księgowych
     * i daty końcowej zbioru wpisów księgowych (np. ABC - RRRR-MM-DD TO: RRRR-MM-DD)
     */
    public String getAbbreviation() {
        return String.format("%s - %s TO: %s", person.getName(), dateFrom.toString(), dateTo.toString());
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public HashSet<Tax> getTaxes() {
        return new HashSet<>(taxes);
    }

    /**
     * Oblicza sumę wartości negatywnych lub pozytywnych wszystkich wpisów księgowych, które są zawarte w danym
     * zbiorze.
     *
     * @param positive Wartość logiczna, przyjmująca wartość true, kiedy metoda ma obliczyć sumę wartości dodatnich;
     *                 false – w przeciwnym razie, kiedy ma obliczyć sumę wartości ujemnych.
     * @return Suma wartości negatywnych lub pozytywnych wszystkich wpisów księgowych, które są zawarte w danym
     * zbiorze.
     */
    private BigDecimal sum(boolean positive) {
        var sum = new BigDecimal(BigInteger.ZERO);

        for (Entry entry : entries) {
            if (positive && entry.getValue().compareTo(BigDecimal.ZERO) > 0) {
                sum = sum.add(entry.getValue());
            } else {
                sum = sum.add(entry.getValue());
            }
        }

        return sum;
    }

    /**
     * Oblicza przychód.
     *
     * @return Przychód uzyskany w danym okresie księgowym.
     */
    public BigDecimal getRevenue() {
        return sum(true);
    }

    /**
     * Oblicza koszt uzyskania przychodu.
     *
     * @return Koszt uzyskania przychodu w danym okresie księgowym.
     */
    public BigDecimal getExpenses() {
        return sum(false);
    }

    /**
     * Oblicza dochód.
     *
     * @return Dochód uzyskany w danym okresie księgowym.
     */
    public BigDecimal getIncome() {
        return getRevenue().subtract(getExpenses());
    }

    /**
     * Oblicza wartość podatku.
     *
     * @param applicableTax Podatek, który ma zastosowanie dla danego okresu księgowego.
     * @return Wartość podatku obliczonego od dochodu osiągniętego w danym okresie księgowym.
     */
    public BigDecimal getTax(Tax applicableTax) {
        return applicableTax.calculateTax(getIncome());
    }

    /**
     * Oblicza dochód po opodatkowaniu.
     *
     * @param applicableTax Podatek, który ma zastosowanie dla danego okresu księgowego.
     * @return Wartość dochodu w danym okresie księgowym pomniejszona o wartość podatku.
     */
    public BigDecimal getAfterTaxIncome(Tax applicableTax) {
        return getIncome().subtract(applicableTax.calculateTax(getIncome()));
    }

    /**
     * Oblicza wartość prowizji od dochodu po opodatkowaniu.
     *
     * @param applicableTax Podatek, który ma zastosowanie dla danego okresu księgowego.
     * @return Wartość prowizji dla danego okresu księgowego.
     */
    public BigDecimal getFee(Tax applicableTax) {
        return getAfterTaxIncome(applicableTax).multiply(BigDecimal.ONE.subtract(person.getFeeRate()));
    }

    /**
     * Tworzy związek powiązania z podatkiem.
     * Wywołuje analogiczną metodę po stronie podatku.
     *
     * @param tax Obiekt reprezentujący podatek, niebędący wartością null.
     */
    public void addTax(Tax tax) {
        if (tax == null) {
            throw new IllegalArgumentException();
        }
        if (!taxes.contains(tax)) {
            taxes.add(tax);
            tax.addPeriodEntrySet(this);
        }
    }

    /**
     * Usuwa związek powiązania z podatkiem.
     * Wywołuje analogiczną metodę po stronie podatku.
     *
     * @param tax Podatek, dla którego istnieje już powiązanie.
     */
    public void removeTax(Tax tax) {
        if (taxes.contains(tax)) {
            taxes.remove(tax);
            tax.removePeriodEntrySet(this);
        }
    }


    /**
     * Zwraca powiązany obiekt menedżera konta.
     * @return Menedżer konta, z którym istnieje powiązanie.
     */
    public AccountManager getAccountManager() {
        return accountManager;
    }


    /**
     * Ustawia powiązanie z obiektem menedżera konta.
     * @param accountManager Menedżer konta, niebędący wartością null.
     */
    public void setAccountManager(AccountManager accountManager) {
        if (accountManager == null) { throw new IllegalArgumentException(); }
        if (accountManager != this.accountManager) {
            this.accountManager = accountManager;
            accountManager.addPeriodEntrySet(this);
        }
    }

    /**
     * Tworzy i dodaje nowy obiekt wpisu księgowego.
     *
     * @param date          Data, kiedy odbyła się transakcja będąca podstawą wpisu księgowego.
     * @param value         Wartość wpisu księgowego – ujemna lub dodatnia.
     * @param justification Opcjonalne uzasadnienie biznesowe dokonanej transakcji.
     */
    public void addEntry(LocalDate date, BigDecimal value, String justification) {
        entries.add(
                new Entry(date, value, justification)
        );
    }

    /**
     * Tworzy i dodaje nowy obiekt wpisu księgowego z pominięciem zadania opcjonalnego atrybutu uzasadnienia
     * biznesowego.
     *
     * @param date  Data, kiedy odbyła się transakcja będąca podstawą wpisu księgowego.
     * @param value Wartość wpisu księgowego – ujemna lub dodatnia.
     */
    public void addEntry(LocalDate date, BigDecimal value) {
        entries.add(
                new Entry(date, value)
        );
    }

    private class Entry implements Serializable {

        // Atrybuty wymagane
        private final LocalDate date;
        private final BigDecimal value;

        // Atrybuty opcjonalne
        private String justification;


        /**
         * Tworzy obiekt reprezentujący wpis księgowy, uwzględniając atrybut opcjonalny justification.
         *
         * @param date          Data transakcji stanowiącej podstawę wpisu księgowego, niebędąca wartością null.
         * @param value         Wartość transakcji stanowiącej podstawę wpisu księgowego, niebędąca wartością null.
         * @param justification Uzasadnienie biznesowe dokonywanego wpisu księgowego.
         */
        public Entry(LocalDate date, BigDecimal value, String justification) {
            if (date == null || value == null ||
                    value.equals(BigDecimal.ZERO)) {
                throw new IllegalArgumentException();
            }
            this.date = date;
            this.value = value;
            this.justification = justification;
        }

        /**
         * Tworzy obiekt reprezentujący wpis księgowy z pominięciem atrybutu opcjonalnego justification.
         *
         * @param date  Data transakcji stanowiącej podstawę wpisu księgowego, niebędąca wartością null.
         * @param value Wartość transakcji stanowiącej podstawę wpisu księgowego, niebędąca wartością null.
         */
        public Entry(LocalDate date, BigDecimal value) {
            if (date == null || value == null ||
                    value.equals(BigDecimal.ZERO)) {
                throw new IllegalArgumentException();
            }
            this.date = date;
            this.value = value;
        }


        public LocalDate getDate() {
            return date;
        }

        public BigDecimal getValue() {
            return value;
        }

        public String getJustification() {
            return justification;
        }

        /**
         * Zmienia lub określa uzasadnienie biznesowe transakcji, stanowiącej podstawę wpisu księgowego.
         *
         * @param justification Uzasadnienie biznesowe dokonywanego wpisu księgowego.
         */
        public void setJustification(String justification) {
            this.justification = justification;
        }
    }
}
