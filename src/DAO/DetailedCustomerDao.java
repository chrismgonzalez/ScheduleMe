package DAO;

import DataModels.Customer;
import DataModels.DetailedCustomer;

public interface DetailedCustomerDao {
    DetailedCustomer getCustomerDetail(Customer customer);
}
