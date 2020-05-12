package DataModels;

import javafx.beans.property.SimpleStringProperty;

public class Customer implements Comparable<Customer> {
    private int customerId;
    private SimpleStringProperty customerName;
    private Integer addressId;
    private Boolean active;

    public Customer(int customerId, String customerName, Integer addressId, Boolean active) {
        this.customerId = customerId;
        this.customerName = new SimpleStringProperty(customerName);
        this.addressId = addressId;
        this.active = active;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;

    }

    public String getCustomerName() {
        return customerName.get();
    }

    public void setCustomerName (String customerName){
        this.customerName.set(customerName);
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return this.getCustomerName();
    }

    @Override
    public int compareTo(Customer other) {
        return this.getCustomerName().compareTo(other.getCustomerName());
    }
}
