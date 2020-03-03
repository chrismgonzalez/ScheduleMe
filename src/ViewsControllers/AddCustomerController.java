package ViewsControllers;

import DAO.CustomerDAO;
import DataModels.*;
import Utils.InputValidator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class AddCustomerController {

    @FXML Button saveNewCustomerButton;
    @FXML Button cancelNewCustomerButton;

    @FXML TextField customerNameTextField;
    @FXML CheckBox customerActiveCheckBox;
    @FXML TextField customerPhoneTextField;
    @FXML TextField customerAddressTextField;
    @FXML TextField customerAddress2TextField;
    @FXML TextField customerCityTextField;
    @FXML TextField customerPostalCodeTextField;
    @FXML TextField customerCountryTextField;

    private Customer addedCustomer;

    public Customer getAddedCustomer() {
        return addedCustomer;
    }

    public void cancelNewCustomer() {
        cancelNewCustomerButton.getScene().getWindow().hide();
    }

    public void saveNewCustomer() {
        String customerName = customerNameTextField.getText().trim();
        boolean isActive = customerActiveCheckBox.isSelected();
        String phone = customerPhoneTextField.getText().trim();
        String addressLine1 = customerAddressTextField.getText().trim();
        String addressLine2 = customerAddress2TextField.getText().trim();
        String cityName = customerCityTextField.getText().trim();
        String postalCode = customerPostalCodeTextField.getText().trim();
        String countryName = customerCountryTextField.getText().trim();

        try {
            InputValidator.validateCustomerInput(customerName, phone, addressLine1, addressLine2, cityName, postalCode, countryName);
        } catch (IllegalArgumentException e) {
            NotificationWindow notificationWindow = new NotificationWindow(e.getMessage());
            notificationWindow.launchAndWait();
            return;
        }

        //save customer
        CustomerDAO customerDao = new CustomerDaoImp();
        addedCustomer = customerDao.addCustomer(customerName, address.getAddressId(), isActive);

        //close window request

        Window window = saveNewCustomerButton.getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

}
