package com.github.bredecorne.masp.controller;

import com.github.bredecorne.masp.model.Address;
import com.github.bredecorne.masp.model.TaxOffice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Objects;

public class TaxOfficeChangeAddressController {

    
    @FXML
    private TableView<TaxOffice> taxOfficeTable;
    @FXML
    private TableColumn<TaxOffice, String> taxOfficeTableColumn;

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
        
    }

    private void populateTaxOfficesTable() {
        taxOfficeTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        ObservableList<TaxOffice> taxOfficeObservableList = FXCollections.observableArrayList(
                FXCollections.observableSet(TaxOffice.getTaxOffices())
        );

        taxOfficeTable.setItems(taxOfficeObservableList);
    }

    private void populateAddressCountryComboBox() {
        var addresses = Address.getAddresses();

        ObservableList<String> countries = FXCollections.observableArrayList(
                addresses.stream()
                        .map(Address::getCountry)
                        .distinct()
                        .toList()
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
                        .toList()
        );

        addressCityComboBox.setItems(cities);
    }

    private void populateStreetComboBox(String selectedCity) {
        var addresses = Address.getAddresses();

        ObservableList<String> streets = FXCollections.observableArrayList(
                addresses.stream()
                        .filter(address -> address.getCity().equals(selectedCity))
                        .map(Address::getStreet)
                        .distinct()
                        .toList()
        );

        addressStreetComboBox.setItems(streets);
    }

    private void populateAddressHouseNumberComboBox(String selectedStreet) {
        var addresses = Address.getAddresses();

        ObservableList<String> houseNumbers = FXCollections.observableArrayList(
                addresses.stream()
                        .filter(address -> address.getStreet().equals(selectedStreet))
                        .map(Address::getHouseNumber)
                        .distinct()
                        .toList()
        );

        addressHouseNumberComboBox.setItems(houseNumbers);
    }

    private void populateAddressApartmentNumberComboBox(String selectedApartment) {
        var addresses = Address.getAddresses();

        ObservableList<String> apartmentNumbers = FXCollections.observableArrayList(
                addresses.stream()
                        .filter(address -> address.getHouseNumber().equals(selectedApartment))
                        .map(Address::getApartmentNumber)
                        .distinct()
                        .toList()
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
            if (newVal != null) {
                populateStreetComboBox(newVal);
            }
        });
    }

    private void setupStreetComboBoxListener() {
        addressStreetComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (!Objects.equals(newVal, oldVal)) {
                addressHouseNumberComboBox.setItems(null);
                addressApartmentNumberComboBox.setItems(null);
            }
            if (newVal != null) {
                populateAddressHouseNumberComboBox(newVal);
            }
        });
    }
    
    private void setupAddressHouseNumberComboBoxListener() {
        addressHouseNumberComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (!Objects.equals(newVal, oldVal)) {
                addressApartmentNumberComboBox.setItems(null);
            }
            if (newVal != null) {
                populateAddressApartmentNumberComboBox(newVal);
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
                    selectedTaxOffice.setAddress(selectedAddress);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Nadano właściwość urzędowi podatkowemu");
                    alert.setHeaderText(null);
                    alert.setContentText(String.format("Adres dla urzędu %s został zaktualizowany, nowy adres to:\n" +
                            "%s, %s, %s, %s/%s.", 
                            selectedTaxOffice.getName(), 
                            selectedTaxOffice.getAddress().getCountry(),
                            selectedTaxOffice.getAddress().getCity(),
                            selectedTaxOffice.getAddress().getStreet(),
                            selectedTaxOffice.getAddress().getHouseNumber(),
                            selectedTaxOffice.getAddress().getApartmentNumber()));
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Błąd");
                    alert.setHeaderText(null);
                    alert.setContentText("Nie wybrano urzędu podatkowego.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText(null);
                alert.setContentText("Wybrany adres jest nieprawidłowy.");
                alert.showAndWait();
            }
        });
    }


}
