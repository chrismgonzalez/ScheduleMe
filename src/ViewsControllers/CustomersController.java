package ViewsControllers;

import DAO.*;
import DataModels.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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
    @FXML ListView<Appointment> customerAppointmentsListView;

    ObservableList<Customer> allCustomers;

    public void initialize() {
        allCustomers = FXCollections.observableArrayList();
        customersListView.setItems(allCustomers);
        //Lambda expression
        customersListView.getOnMouseClicked(e -> {
            if (customersListView.getSelectionModel().getSelectedItem() != null) {
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

    public void addNewCustomer() {
        Parent root;
        AddCustomerController controller;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCustomerView.fxml"));
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e){
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            Customer newCustomer = controller.getAddedCustomer();
            if (newCustomer != null) {
                allCustomers.add(newCustomer);
                customersListView.getSelectionModel().select(newCustomer);
                populateSelectedCustomerLabelText();
                Collections.sort(allCustomers);
            }
        });
        stage.show();
    }
    public void beginEditSelectedCustomer() {
        Customer selectedCustomer = customersListView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            return;
        }
        toggleDisplayEditCustomerUiElements(true);
        populateSelectedCustomerEditableFields();
    }
    public void cancelEditSelectedCustomer() {
        toggleDisplayEditCustomerUiElements(false);
        TextField[] customerTextFields = {customerNameTextField, customerPhoneTextField, customerAddressTextField, customerAddress2TextField, customerCityTextField, customerPostalCodeTextField, customerCountryTextField};
        for (TextField textField : customerTextFields) {
            textField.clear();
        }
        customerActiveCheckbox.setSelected(false);
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
        DetailedCustomer selectedDetailedCustomer = getSelectedCustomerDetail();
        customerNameLabel.setText(selectedDetailedCustomer.getCustomerName());
        customerActiveLabel.setText(selectedDetailedCustomer.getActive().toString());
        customerPhoneLabel.setText(selectedDetailedCustomer.getAddress().getPhone());
        customerAddressLabel.setText(selectedDetailedCustomer.getAddress().getAddress());
        customerAddress2Label.setText(selectedDetailedCustomer.getAddress().getAddress2());
        customerCityLabel.setText(selectedDetailedCustomer.getCity().getCity());
        customerPostalCodeLabel.setText(selectedDetailedCustomer.getAddress().getPostalCode());
        customerCountryLabel.setText(selectedDetailedCustomer.getCountry().getCountry());
    }

    private void populateSelectedCustomerEditableFields() {

    }

    private void populateSelectedCustomerAppointments() {
        Customer selectedCustomer = customersListView.getSelectionModel().getSelectedItem();
        AppointmentDao appointmentDao = new AppointmentDaoImp();
        ObservableList<Appointment> selectedCustomerAppointments = appointmentDao.getAppointmentsByCustomerId(selectedCustomer.getCustomerId());
        Collections.sort(selectedCustomerAppointments);
        customerAppointmentsListView.setItems(selectedCustomerAppointments);
    }

    private void toggleDisplayEditCustomerUiElements(boolean isOn) {

    }

    public void addAppointment() {

    }

    public void viewAppointment() {

    }
}
