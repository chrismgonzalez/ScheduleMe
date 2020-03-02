package DAO;

import DataModels.Customer;
import javafx.collections.ObservableList;

public interface CustomerDAO {
    ObservableList<Customer> getAllCustomers();
    Customer getCustomerById(int customerId);
    boolean updateCustomer(int CustomerId, String customerName, int addressId, boolean active );
    boolean deleteCustomer(Customer customer);
    Customer addCustomer(String customerName, int addressId, boolean active);
}
