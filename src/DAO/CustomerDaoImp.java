package DAO;

import DataModels.Customer;
import Utils.DbConnection;
import Utils.UserSettings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDaoImp implements CustomerDao  {

    @Override
    public ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String query = "select customerId, customerName, addressId, active from customer";
        try {
            ResultSet resultSet = DbConnection.getInstance().connection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                int customerId = resultSet.getInt("customerId");
                String customerName = resultSet.getString("customerName");
                Integer addressId = resultSet.getInt("addressId");
                Boolean active = resultSet.getInt("active") > 0;
                Customer customer = new Customer(customerId, customerName, addressId, active);
                allCustomers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomers;
    }

    @Override
    public Customer getCustomerById(int customerId) {
        Customer customer = null;
        String query = "select customerName, addressId, active from customer where customerId=?";
        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            ps.setInt(1, customerId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String customerName = resultSet.getString("customerName");
                Integer addressId = resultSet.getInt("addressId");
                Boolean active = resultSet.getInt("active") > 0;
                customer = new Customer(customerId, customerName, addressId, active);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public boolean updateCustomer(int customerId, String customerName, int addressId, boolean active) {
        boolean updated = false;
        String query = "update customer set customerName=?, addressId=?, active=?, lastUpdate=now(), lastUpdateBy=? " +
                "where customerId=?";
        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            ps.setString(1, customerName);
            ps.setInt(2, addressId);
            ps.setInt(3, active ? 1 : 0);
            ps.setString(4, UserSettings.userName);
            ps.setInt(5, customerId);
            updated = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updated;
    }

    @Override
    public boolean deleteCustomer(Customer customer) {
        String query = "delete from customer where customerId = ?";
        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            ps.setInt(1, customer.getCustomerId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Customer addCustomer(String customerName, int addressId, boolean active) {
        Customer customer = null;
        String query = "insert into customer (customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                "values(?, ?, ?, ?, now(), ?, now(), ?)";
        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            int customerId = getNextPrimaryKey();
            ps.setInt(1, customerId);
            ps.setString(2, customerName);
            ps.setInt(3, addressId);
            ps.setInt(4, active ? 1 : 0);
            ps.setString(5, UserSettings.userName);
            ps.setString(6, UserSettings.userName);
            ps.executeUpdate();
            customer = new Customer(customerId, customerName, addressId, active);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    private int getNextPrimaryKey() {
        int nextPk = -1;
        String query = "select max(customerId) from customer";
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
