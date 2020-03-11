package ViewsControllers;

import DAO.*;
import DataModels.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.util.Collections;
import java.util.stream.Collectors;


public class CustomersController extends InnerController {
    @FXML TextField searchCustomersTextField;
    @FXML Button searchCustomersButton;
    @FXML Button newCustomerButton;
    @FXML ListView<Customer> customersListView;

    @FXML Button editCustomerButton;
    @FXML Button deleteCustomerButton;
    @FXML Button saveCustomerButton;
    @FXML Button cancelEditCustomerButton;

    @FXML Label customerNameLabel;
    @FXML Label customerActiveLabel;
    @FXML Label customerPhoneLabel;
    @FXML Label customerAddressLabel;
    @FXML Label customerAddress2Label;
    @FXML Label customerCityLabel;
    @FXML Label customerPostalCodeLabel;
    @FXML Label customerCountryLabel;

    @FXML TextField customerNameTextField;
    @FXML CheckBox customerActiveCheckbox;
    @FXML TextField customerPhoneTextField;
    @FXML TextField customerAddressTextField;
    @FXML TextField customerAddress2TextField;
    @FXML TextField customerCityTextField;
    @FXML TextField customerPostalCodeTextField;
    @FXML TextField customerCountryTextField;

    @FXML Button addAppointmentButton;
    @FXML Button viewAppointmentButton;
    @FXML Listview<Appointment> customerAppointmentsListView;

    ObservableList<Customer> allCustomers;

    public void initialize() {
        allCustomers = FXCollections.observableArrayList();
        customersListView.setItems(allCustomers);
        //Lambda expression
        customersListView.getOnMouseClicked(e -> {
            if(customersListView.getSelectionModel().getSelectedItem() != null) {
                populateSelectedCustomerLabelText();
                populateSelectedCustomerAppointments();
            }
        });
        refreshCustomersList();
    }

    public void searchCustomers() {
        String searchText = searchCustomersTextField.getText().trim().toLowerCase();
        if (searchText.equals("")) {
            customersListView.setItems(allCustomers);
        } else {
            //lambda expression here
            ObservableList<Customer> customersFound = FXCollections.observableArrayList(
              allCustomers.stream().filter(c -> c.getCustomerName().trim().toLowerCase().contains(searchText))
                    .sorted()
                    .collect(Collectors.toList())
            );
            customersListView.setItems(customersFound);
        }
    }

    public void  addNewCustomer() {
        Parent root;

    }

    public void cancelEditSelectedCustomer() {

    }

    public void saveEditSelectedCustomer() {
        //set fields
        //validate input
        //get customer details
        //update country
        //update city
        //update address
        //update customer
        //refresh customer data and exit the edit view
    }

    private DetailedCustomer getSelectedCustomerDetail() {

    }

    private void refreshCustomersList() {

    }

    private void clearCustomerSelection() {

    }

    private void populateSelectedCustomerLabelText() {

    }

    private void populateSelectedCustomerEditableFields() {

    }

    private void populateSelectedCustomerAppointments() {

    }

    private void toggleDisplayEditCustomerUiElements(boolean isOn) {

    }

    public void addAppointment() {

    }

    public void viewAppointment() {

    }
}
