package com.github.bredecorne.masp.utils;

import com.github.bredecorne.masp.model.AccountManager;
import com.github.bredecorne.masp.model.Address;
import com.github.bredecorne.masp.model.PeriodEntrySet;
import com.github.bredecorne.masp.model.TaxOffice;
import com.github.bredecorne.masp.model.persons.Person;
import com.github.bredecorne.masp.model.taxes.Tax;

import java.io.*;
import java.util.HashSet;

public class Repository {

    private static final String REPOSITORY_FILEPATH = "repository.data";

    /**
     * Tworzy nowy obiekt, przechowujący ogół ekstensji klas składających się na aplikację oraz dokonuje ich
     * serializacji do pliku określonego zmienną REPOSITORY_FILENAME
     */
    public static void serialize() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(REPOSITORY_FILEPATH)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(
                    new DataTransferObject(
                            Person.getPersons(),
                            Tax.getTaxes(),
                            AccountManager.getAccountManagers(),
                            Address.getAddresses(),
                            PeriodEntrySet.getPeriodEntrySets(),
                            TaxOffice.getTaxOffices()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Odczytuje dane z pliku zawierającego obiekty zapisane przy użyciu metody serialize() oraz nadpisuje
     * ekstensje wszystkich klas, które ją posiadają i są obsługiwane przez serializację.
     */
    public static void deserialize() {
        try (FileInputStream fileInputStream = new FileInputStream(REPOSITORY_FILEPATH)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            DataTransferObject dto = (DataTransferObject) objectInputStream.readObject();

            Person.setPersons(dto.persons);
            Tax.setTaxes(dto.taxes);
            AccountManager.setAccountManagers(dto.accountManagers);
            Address.setAddresses(dto.addresses);
            PeriodEntrySet.setPeriodEntrySets(dto.periodEntrySets);
            TaxOffice.setTaxOffices(dto.taxOffices);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private record DataTransferObject(HashSet<Person> persons, HashSet<Tax> taxes,
                                      HashSet<AccountManager> accountManagers,
                                      HashSet<Address> addresses, HashSet<PeriodEntrySet> periodEntrySets,
                                      HashSet<TaxOffice> taxOffices) implements Serializable {
    }
}
