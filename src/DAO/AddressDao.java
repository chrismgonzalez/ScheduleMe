package DAO;

import DataModels.Address;
import DataModels.Appointment;
import javafx.collections.ObservableList;

public interface AddressDao {
    ObservableList<Address> getAllAddresses();
    Address getAddressByAttributes(String addressLine1, String addressLine2, int cityId, String postalCode, String Phone);
    boolean updateAddress(Address address);
    boolean updateAddress(int addressId, String addressLine1, String addressLine2, int cityId, String postalCode, String Phone);
    boolean deleteAddress(Address address);
    Address addAddress(String address, String address2, int cityId, String postalCode, String phone);
}
