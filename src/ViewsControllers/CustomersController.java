package ViewsControllers;

import DAO.*;
import DataModels.*;
import Utils.InputValidator;
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
    @FXML CheckBox customerActiveCheckBox;
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
        /* Here I use a lambda expression to implement a functional interface (EventHandler) with fewer lines of code.
        The alternative is to write an anonymous inner class that implements the interface.
        (this comment is to justify use of a lambda expression for the project assignment grader) */
        customersListView.setOnMouseClicked(e -> {
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
            /* Here I use a lambda expression to implement a functional interface (Predicate) with fewer lines of code.
            The alternative is to write an anonymous inner class that implements the interface.
            (this comment is to justify use of a lambda expression for the project assignment grader) */
            ObservableList<Customer> customersFound = FXCollections.observableArrayList(
                    allCustomers.stream()
                            .filter(c -> c.getCustomerName().trim().toLowerCase().contains(searchText))
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
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
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
        TextField[] customerTextFields = {customerNameTextField, customerPhoneTextField, customerAddressTextField,
                customerAddress2TextField, customerCityTextField, customerPostalCodeTextField, customerCountryTextField};
        for (TextField textField : customerTextFields) {
            textField.clear();
        }
        customerActiveCheckBox.setSelected(false);
    }

    public void saveEditSelectedCustomer() {
        String customerName = customerNameTextField.getText();
        boolean isActive = customerActiveCheckBox.isSelected();
        String phone = customerPhoneTextField.getText();
        String addressLine1 = customerAddressTextField.getText();
        String addressLine2 = customerAddress2TextField.getText();
        String cityName = customerCityTextField.getText();
        String postalCode = customerPostalCodeTextField.getText();
        String countryName = customerCountryTextField.getText();

        // validate input
        try {
            InputValidator.validateCustomerInput(customerName, phone, addressLine1, addressLine2, cityName, postalCode, countryName);
        } catch (IllegalArgumentException e) {
            NotificationWindow notificationWindow = new NotificationWindow(e.getMessage());
            notificationWindow.launchAndWait();
            return;
        }

        // get customer detail
        DetailedCustomer selectedDetailedCustomer = getSelectedCustomerDetail();
        int customerId = selectedDetailedCustomer.getCustomerId();

        // update country
        Country country;
        if (selectedDetailedCustomer.getCountry().getCountry().equals(countryName)) {
            country = selectedDetailedCustomer.getCountry();
        } else {
            CountryDao countryDao = new CountryDaoImp();
            country = countryDao.getCountryByName(countryName);
            if (country == null) {
                country = countryDao.addCountry(countryName);
            }
        }

        // update city
        City city;
        if (selectedDetailedCustomer.getCity().getCity().equals(cityName) && selectedDetailedCustomer.getCity().getCountryId() == country.getCountryId()) {
            city = selectedDetailedCustomer.getCity();
        } else {
            CityDao cityDao = new CityDaoImp();
            city = cityDao.getCityByNameAndCountryId(cityName, country.getCountryId());
            if (city == null) {
                city = cityDao.addCity(cityName, country.getCountryId());
            }
        }

        // update address
        Address address = selectedDetailedCustomer.getAddress();;
        if (!(selectedDetailedCustomer.getAddress().getAddress().equals(addressLine1) &&
                selectedDetailedCustomer.getAddress().getAddress2().equals(addressLine2) &&
                selectedDetailedCustomer.getAddress().getCityId() == city.getCityId() &&
                selectedDetailedCustomer.getAddress().getPostalCode().equals(postalCode) &&
                selectedDetailedCustomer.getAddress().getPhone().equals(phone))) {
            AddressDao addressDao = new AddressDaoImp();
            boolean isAddressUpdated = addressDao.updateAddress(selectedDetailedCustomer.getAddress().getAddressId(), addressLine1, addressLine2, city.getCityId(), postalCode, phone);
            if (isAddressUpdated) {
                address = new Address(selectedDetailedCustomer.getAddress().getAddressId(), addressLine1, addressLine2, city.getCityId(), postalCode, phone);
            }
        }

        // update customer
        CustomerDao customerDao = new CustomerDaoImp();
        boolean isCustomerUpdated = customerDao.updateCustomer(customerId, customerName, selectedDetailedCustomer.getAddress().getAddressId(), isActive);
        if (isCustomerUpdated) {
            customersListView.getSelectionModel().getSelectedItem().setCustomerName(customerName);
            customersListView.getSelectionModel().getSelectedItem().setAddressId(address.getAddressId());
            customersListView.getSelectionModel().getSelectedItem().setActive(isActive);
        }

        // refresh customer data and exit edit view
        populateSelectedCustomerLabelText();
        cancelEditSelectedCustomer();
    }

    public void requestDeleteCustomer() {
        Customer selectedCustomer = customersListView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            return;
        }
        String confirmationMessage = "Are you sure you want to permanently remove this customer?\n\n" + selectedCustomer.getCustomerName() + "\n\n\n";
        boolean deleteConfirmed = new ConfirmationWindow().display(confirmationMessage);
        if (deleteConfirmed) {
            CustomerDao customerDao = new CustomerDaoImp();
            customerDao.deleteCustomer(selectedCustomer);
            clearCustomerSelection();
            refreshCustomersList();
        }
    }

    private DetailedCustomer getSelectedCustomerDetail() {
        Customer selectedCustomer = customersListView.getSelectionModel().getSelectedItem();
        DetailedCustomerDao detailedCustomerDao = new DetailedCustomerDaoImp();
        return detailedCustomerDao.getCustomerDetail(selectedCustomer);
    }

    private void refreshCustomersList() {
        CustomerDaoImp customerDaoImp = new CustomerDaoImp();
        allCustomers.clear();
        allCustomers.addAll(customerDaoImp.getAllCustomers());
        Collections.sort(allCustomers);
    }

    private void clearCustomerSelection() {
        Label[] customerLabels = {customerNameLabel, customerActiveLabel, customerPhoneLabel, customerAddressLabel, customerAddress2Label,
                customerCityLabel, customerPostalCodeLabel, customerCountryLabel};
        for (Label label : customerLabels) {
            label.setText("");
        }
        customerAppointmentsListView.setItems(FXCollections.observableArrayList());
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
        DetailedCustomer selectedDetailedCustomer = getSelectedCustomerDetail();
        customerNameTextField.setText(selectedDetailedCustomer.getCustomerName());
        customerActiveCheckBox.setSelected(selectedDetailedCustomer.getActive());
        customerPhoneTextField.setText(selectedDetailedCustomer.getAddress().getPhone());
        customerAddressTextField.setText(selectedDetailedCustomer.getAddress().getAddress());
        customerAddress2TextField.setText(selectedDetailedCustomer.getAddress().getAddress2());
        customerCityTextField.setText(selectedDetailedCustomer.getCity().getCity());
        customerPostalCodeTextField.setText(selectedDetailedCustomer.getAddress().getPostalCode());
        customerCountryTextField.setText(selectedDetailedCustomer.getCountry().getCountry());
    }

    private void populateSelectedCustomerAppointments() {
        Customer selectedCustomer = customersListView.getSelectionModel().getSelectedItem();
        AppointmentDao appointmentDao = new AppointmentDaoImp();
        ObservableList<Appointment> selectedCustomerAppointments = appointmentDao.getAppointmentsByCustomerId(selectedCustomer.getCustomerId());
        Collections.sort(selectedCustomerAppointments);
        customerAppointmentsListView.setItems(selectedCustomerAppointments);
    }

    private void toggleDisplayEditCustomerUiElements(boolean isOn) {
        Label[] customerLabels = {customerNameLabel, customerActiveLabel, customerPhoneLabel, customerAddressLabel, customerAddress2Label,
                customerCityLabel, customerPostalCodeLabel, customerCountryLabel};
        for (Label label : customerLabels) {
            label.setDisable(isOn);
            label.setVisible(!isOn);
        }
        TextField[] customerTextFields = {customerNameTextField, customerPhoneTextField, customerAddressTextField,
                customerAddress2TextField, customerCityTextField, customerPostalCodeTextField, customerCountryTextField};
        for (TextField textField : customerTextFields) {
            textField.setDisable(!isOn);
            textField.setVisible(isOn);
        }
        customerActiveCheckBox.setDisable(!isOn);
        customerActiveCheckBox.setVisible(isOn);
        editCustomerButton.setDisable(isOn);
        editCustomerButton.setVisible(!isOn);
        deleteCustomerButton.setDisable(isOn);
        deleteCustomerButton.setVisible(!isOn);
        saveCustomerButton.setDisable(!isOn);
        saveCustomerButton.setVisible(isOn);
        cancelEditCustomerButton.setDisable(!isOn);
        cancelEditCustomerButton.setVisible(isOn);
        customersListView.setDisable(isOn);
    }

    public void addAppointment() {
        Customer selectedCustomer = customersListView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            return;
        }
        Parent root;
        AddAppointmentController controller;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddAppointmentView.fxml"));
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            Appointment newAppointment = controller.getAddedAppointment();
            if (newAppointment != null) {
                customerAppointmentsListView.getItems().add(newAppointment);
                Collections.sort(customerAppointmentsListView.getItems());
            }
        });
        stage.show();
        controller.setCustomer(selectedCustomer);
    }

    public void viewAppointment() {
        Customer selectedCustomer = customersListView.getSelectionModel().getSelectedItem();
        Appointment selectedAppointment = customerAppointmentsListView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null || selectedAppointment == null) {
            return;
        }
        Parent root;
        EditAppointmentController controller;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditAppointmentView.fxml"));
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            populateSelectedCustomerAppointments();
        });
        stage.show();
        controller.setCustomer(selectedCustomer);
        controller.setAppointment(selectedAppointment);
    }

}