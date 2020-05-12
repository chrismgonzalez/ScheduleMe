package DataModels;

import javafx.beans.property.SimpleStringProperty;

public class DetailedCustomer extends Customer {
    private Address address;
    private City city;
    private Country country;

    public DetailedCustomer(Integer customerId, String customerName, Integer addressId, Boolean active, Address address, City city, Country country) {
        super(customerId, customerName, addressId, active);
        this.address = address;
        this.city = city;
        this.country = country;
    }

    public DetailedCustomer(Customer customer, Address address, City city, Country country) {
        super(customer.getCustomerId(), customer.getCustomerName(), customer.getAddressId(), customer.getActive());
        this.address = address;
        this.city = city;
        this.country = country;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

}
