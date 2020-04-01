package DAO;

import DataModels.Address;
import Utils.DbConnection;
import Utils.UserSettings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDaoImp implements AddressDao {

    @Override
    public ObservableList<Address> getAllAddresses() {
        ObservableList<Address> allAddresses = FXCollections.observableArrayList();
        String query = "select addressId, address, address2, cityId, postalCode, phone from address";
        try {
            ResultSet resultSet = DbConnection.getInstance().connection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                int addressId = resultSet.getInt("addressId");
                String address_line1 = resultSet.getString("address");
                String address_line2 = resultSet.getString("address2");
                int cityId = resultSet.getInt("cityId");
                String postalCode = resultSet.getString("postalCode");
                String phone = resultSet.getString("phone");
                Address address = new Address(addressId, address_line1, address_line2, cityId, postalCode, phone);
                allAddresses.add(address);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAddresses;
    }

    @Override
    public Address getAddressByAttributes(String addressLine1, String addressLine2, int cityId, String postalCode, String phone) {
        Address address = null;
        String query = "select addressId from address " +
                        "where address=? and address2=? and cityId=? and postalCode=? and phone=?";
        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            ps.setString(1, addressLine1);
            ps.setString(2, addressLine2);
            ps.setInt(3, cityId);
            ps.setString(4, postalCode);
            ps.setString(5, phone);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()) {
                int addressId = resultSet.getInt("addressId");
                address = new Address(addressId, addressLine1, addressLine2, cityId, postalCode, phone);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return address;
    }

    @Override
    public boolean updateAddress(Address address) {
        String query = "update address set address=?, address2=?, cityId=?, postalCode=?, phone=? " +
                        "where addressId=?";
        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            ps.setString(1, address.getAddress());
            ps.setString(2, address.getAddress2());
            ps.setInt(3, address.getCityId());
            ps.setString(4, address.getPostalCode());
            ps.setString(5, address.getPhone());
            ps.setInt(6, address.getAddressId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateAddress(int addressId, String addressLine1, String addressLine2, int cityId, String postalCode, String phone) {
        String query = "update address set address=?, address2=?, cityId=?, postalCode=?, phone=? " +
                "where addressId=?";
        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            ps.setString(1, addressLine1);
            ps.setString(2, addressLine2);
            ps.setInt(3, cityId);
            ps.setString(4, postalCode);
            ps.setString(5, phone);
            ps.setInt(6, addressId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAddress(Address address) {
        String query = "delete from address where addressId = ?";
        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            ps.setInt(1, address.getAddressId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Address addAddress(String addressLine1, String addressLine2, int cityId, String postalCode, String phone) {
        Address address = null;
        String query = "insert into address (addressId, address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                "values(?, ?, ?, ?, ?, ?, now(), ?, now(), ?)";
        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            int addressId = getNextPrimaryKey();
            ps.setInt(1, addressId);
            ps.setString(2, addressLine1);
            ps.setString(3, addressLine2);
            ps.setInt(4, cityId);
            ps.setString(5, postalCode);
            ps.setString(6, phone);
            ps.setString(7, UserSettings.userName);
            ps.setString(8, UserSettings.userName);
            ps.executeUpdate();
            address = new Address(addressId, addressLine1, addressLine2, cityId, postalCode, phone);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return address;
    }

    private int getNextPrimaryKey() {
        int nextPk = -1;
        String query = "select max(addressId) from address";
        try {
            ResultSet resultSet = DbConnection.getInstance().connection().createStatement().executeQuery(query);
            if (resultSet.next()) {
                nextPk = resultSet.getInt(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getSQLState());
            System.out.println(e.getErrorCode());
        }
        return nextPk;
    }
}
