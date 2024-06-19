package com.github.bredecorne.masp.controller;

import com.github.bredecorne.masp.model.Address;
import com.github.bredecorne.masp.model.TaxOffice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    public void initialize() {
        populateTaxOfficesTable();
        populateAddressComboBoxes();
    }

    @FXML
    public void populateTaxOfficesTable() {
        taxOfficeTableColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        
        ObservableList<TaxOffice> taxOfficeObservableList = FXCollections.observableArrayList(
                FXCollections.observableSet(TaxOffice.getTaxOffices())
        );
        
        taxOfficeTable.setItems(taxOfficeObservableList);
    }

    @FXML
    public void populateAddressComboBoxes() {
        var addresses = Address.getAddresses();

        ObservableList<String> countries = FXCollections.observableArrayList(
                addresses.stream()
                        .map(Address::getCountry)
                        .distinct()
                        .toList()
        );

        addressCountryComboBox.setItems(countries);
    }

}
