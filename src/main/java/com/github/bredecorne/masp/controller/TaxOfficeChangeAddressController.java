package com.github.bredecorne.masp.controller;

import com.github.bredecorne.masp.model.Address;
import com.github.bredecorne.masp.model.TaxOffice;
import com.github.bredecorne.masp.utils.Repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Objects;
import java.util.stream.Collectors;

public class TaxOfficeChangeAddressController {

    @FXML
    private TableView<TaxOffice> taxOfficeTable;
    @FXML
    private TableColumn<TaxOffice, String> taxOfficeTableColumn;

    @FXML
    private TableView<Address> addressesTable;
    @FXML
    private TableColumn<Address, String> addressesTableColumn;

    @FXML
    private ComboBox<String> addressCountryComboBox;
    @FXML
    private ComboBox<String> addressCityComboBox;
    @FXML
    private ComboBox<String> addressHouseNumberComboBox;
    @FXML
    private ComboBox<String> addressApartmentNumberComboBox;
    @FXML
    private ComboBox<String> addressStreetComboBox;

    @FXML
    private Button addressSendButton;
    @FXML
    private Button addressRemoveButton;

    @FXML
    private MenuItem saveMenu;
    @FXML
    private MenuItem loadMenu;

    @FXML
    public void initialize() {
        // Initial data
        populateTaxOfficesTable();
        populateAddressCountryComboBox();

        // Listeners
        setupCountryComboBoxListener();
        setupCityComboBoxListener();
        setupStreetComboBoxListener();
        setupAddressHouseNumberComboBoxListener();
        setupAddressSendButtonListener();
        setupSaveMenuItemListener();
        setupLoadMenuItemListener();
        setupTaxOfficeTableListener();
        setupAddressRemoveButtonListener();
    }

    private void populateTaxOfficesTable() {
        taxOfficeTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        ObservableList<TaxOffice> taxOfficeObservableList = FXCollections.observableArrayList(
                TaxOffice.getTaxOffices()
        );

        taxOfficeTable.setItems(taxOfficeObservableList);
    }

    private void populateAddressesTable(TaxOffice taxOffice) {
        addressesTableColumn.setCellValueFactory(cellData -> {
            Address address = cellData.getValue();
            String fullAddress = String.format("%s %s, %s, %s/%s",
                    address.getCountry(),
                    address.getCity(),
                    address.getStreet(),
                    address.getHouseNumber(),
                    address.getApartmentNumber());
            return new javafx.beans.property.SimpleStringProperty(fullAddress);
        });

        ObservableList<Address> addressesObservableList = FXCollections.observableArrayList(
                taxOffice.getAddresses()
        );

        addressesTable.setItems(addressesObservableList);
    }

    private void populateAddressCountryComboBox() {
        var addresses = Address.getAddresses();

        ObservableList<String> countries = FXCollections.observableArrayList(
                addresses.stream()
                        .map(Address::getCountry)
                        .distinct()
                        .collect(Collectors.toList())
        );

        addressCountryComboBox.setItems(countries);
    }

    private void populateCityComboBox(String selectedCountry) {
        var addresses = Address.getAddresses();

        ObservableList<String> cities = FXCollections.observableArrayList(
                addresses.stream()
                        .filter(address -> address.getCountry().equals(selectedCountry))
                        .map(Address::getCity)
                        .distinct()
                        .collect(Collectors.toList())
        );

        addressCityComboBox.setItems(cities);
    }

    private void populateStreetComboBox(String selectedCountry, String selectedCity) {
        var addresses = Address.getAddresses();

        ObservableList<String> streets = FXCollections.observableArrayList(
                addresses.stream()
                        .filter(address -> address.getCountry().equals(selectedCountry) && 
                                address.getCity().equals(selectedCity))
                        .map(Address::getStreet)
                        .distinct()
                        .collect(Collectors.toList())
        );

        addressStreetComboBox.setItems(streets);
    }

    private void populateAddressHouseNumberComboBox(String selectedCountry, String selectedCity, String selectedStreet) {
        var addresses = Address.getAddresses();

        ObservableList<String> houseNumbers = FXCollections.observableArrayList(
                addresses.stream()
                        .filter(address -> address.getCountry().equals(selectedCountry) && 
                                address.getCity().equals(selectedCity) && 
                                address.getStreet().equals(selectedStreet))
                        .map(Address::getHouseNumber)
                        .distinct()
                        .collect(Collectors.toList())
        );

        addressHouseNumberComboBox.setItems(houseNumbers);
    }

    private void populateAddressApartmentNumberComboBox(String selectedCountry, String selectedCity, String selectedStreet, String selectedHouseNumber) {
        var addresses = Address.getAddresses();

        ObservableList<String> apartmentNumbers = FXCollections.observableArrayList(
                addresses.stream()
                        .filter(address -> address.getCountry().equals(selectedCountry) && 
                                address.getCity().equals(selectedCity) && 
                                address.getStreet().equals(selectedStreet) && 
                                address.getHouseNumber().equals(selectedHouseNumber))
                        .map(Address::getApartmentNumber)
                        .distinct()
                        .collect(Collectors.toList())
        );

        addressApartmentNumberComboBox.setItems(apartmentNumbers);
    }

    private void setupCountryComboBoxListener() {
        addressCountryComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (!Objects.equals(newVal, oldVal)) {
                addressCityComboBox.setItems(null);
                addressStreetComboBox.setItems(null);
                addressHouseNumberComboBox.setItems(null);
                addressApartmentNumberComboBox.setItems(null);
            }
            if (newVal != null) {
                populateCityComboBox(newVal);
            }
        });
    }

    private void setupCityComboBoxListener() {
        addressCityComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (!Objects.equals(newVal, oldVal)) {
                addressStreetComboBox.setItems(null);
                addressHouseNumberComboBox.setItems(null);
                addressApartmentNumberComboBox.setItems(null);
            }
            String selectedCountry = addressCountryComboBox.getSelectionModel().getSelectedItem();
            if (newVal != null && selectedCountry != null) {
                populateStreetComboBox(selectedCountry, newVal);
            }
        });
    }

    private void setupStreetComboBoxListener() {
        addressStreetComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (!Objects.equals(newVal, oldVal)) {
                addressHouseNumberComboBox.setItems(null);
                addressApartmentNumberComboBox.setItems(null);
            }
            String selectedCountry = addressCountryComboBox.getSelectionModel().getSelectedItem();
            String selectedCity = addressCityComboBox.getSelectionModel().getSelectedItem();
            if (newVal != null && selectedCountry != null && selectedCity != null) {
                populateAddressHouseNumberComboBox(selectedCountry, selectedCity, newVal);
            }
        });
    }

    private void setupAddressHouseNumberComboBoxListener() {
        addressHouseNumberComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (!Objects.equals(newVal, oldVal)) {
                addressApartmentNumberComboBox.setItems(null);
            }
            String selectedCountry = addressCountryComboBox.getSelectionModel().getSelectedItem();
            String selectedCity = addressCityComboBox.getSelectionModel().getSelectedItem();
            String selectedStreet = addressStreetComboBox.getSelectionModel().getSelectedItem();
            if (newVal != null && selectedCountry != null && selectedCity != null && selectedStreet != null) {
                populateAddressApartmentNumberComboBox(selectedCountry, selectedCity, selectedStreet, newVal);
            }
        });
    }

    private void setupAddressSendButtonListener() {
        addressSendButton.setOnAction(event -> {
            String selectedCountry = addressCountryComboBox.getSelectionModel().getSelectedItem();
            String selectedCity = addressCityComboBox.getSelectionModel().getSelectedItem();
            String selectedStreet = addressStreetComboBox.getSelectionModel().getSelectedItem();
            String selectedHouseNumber = addressHouseNumberComboBox.getSelectionModel().getSelectedItem();
            String selectedApartmentNumber = addressApartmentNumberComboBox.getSelectionModel().getSelectedItem();

            Address selectedAddress = Address.getAddresses().stream()
                    .filter(address ->
                            address.getCountry().equals(selectedCountry) &&
                                    address.getCity().equals(selectedCity) &&
                                    address.getStreet().equals(selectedStreet) &&
                                    address.getHouseNumber().equals(selectedHouseNumber) &&
                                    address.getApartmentNumber().equals(selectedApartmentNumber)
                    )
                    .findFirst()
                    .orElse(null);

            if (selectedAddress != null) {
                TaxOffice selectedTaxOffice = taxOfficeTable.getSelectionModel().getSelectedItem();
                if (selectedTaxOffice != null) {
                    selectedTaxOffice.addAddress(selectedAddress);

                    if (selectedTaxOffice.getAddresses().contains(selectedAddress)) {
                        showAlert(Alert.AlertType.INFORMATION, "Nadano właściwość", null,
                                "Dodano nowy adres dla danego urzędu.");
                        populateAddressesTable(selectedTaxOffice);
                    }
                } else {
                    showAlert(Alert.AlertType.INFORMATION, "Błąd", null,
                            "Nie wskazano urzędu podatkowego.");
                }
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Błąd", null,
                        "Wybrany adres jest nieprawidłowy.");
            }
        });
    }

    private void setupSaveMenuItemListener() {
        saveMenu.setOnAction(event -> {
            Repository.serialize();
            showAlert(Alert.AlertType.INFORMATION, "Zapisano dane", null,
                    "Zapisano dane do repozytorium.");
        });
    }

    private void setupLoadMenuItemListener() {
        loadMenu.setOnAction(event -> {
            Repository.deserialize();
            showAlert(Alert.AlertType.INFORMATION, "Zaktualizowano dane", null,
                    "Pobrano dane z repozytorium.");
            populateTaxOfficesTable();
        });
    }

    private void setupTaxOfficeTableListener() {
        taxOfficeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                populateAddressesTable(newVal);
            }
        });
    }

    private void setupAddressRemoveButtonListener() {
        addressRemoveButton.setOnAction(event -> {
            if (taxOfficeTable.getSelectionModel().getSelectedItem() != null) {
                TaxOffice selectedTaxOffice = taxOfficeTable.getSelectionModel().getSelectedItem();
                if (addressesTable.getSelectionModel().getSelectedItem() != null) {
                    selectedTaxOffice.removeAddress(addressesTable.getSelectionModel().getSelectedItem());
                    showAlert(Alert.AlertType.INFORMATION, "Usunięto", null,
                            "Usunięto właściwość urzędu podatkowego dla adresu.");
                    populateAddressesTable(selectedTaxOffice);
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Błąd", "null",
                        "Nie wskazano urzędu podatkowego lub adresu.");
            }
        });
    }

    public void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
