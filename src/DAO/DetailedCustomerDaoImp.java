package DAO;

import DataModels.*;
import Utils.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DetailedCustomerDaoImp implements DetailedCustomerDao{
    @Override
    public DetailedCustomer getCustomerDetail(Customer customer) {
        DetailedCustomer detailedCustomer = null;
        String query = "select address, address2, address.cityId, postalCode, phone, city, city.countryId, country " +
                "from address " +
                "left join city on address.cityId=city.cityId " +
                "left join country on city.countryId=country.countryId " +
                "where addressId=" + customer.getAddressId();
        try {
            ResultSet resultSet = DbConnection.getInstance().connection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                String address_line1 = resultSet.getString("address");
                String address_line2 = resultSet.getString("address2");
                Integer cityId = resultSet.getInt("cityId");
                String postalCode = resultSet.getString("postalCode");
                String phone = resultSet.getString("phone");
                String cityName = resultSet.getString("city");
                Integer countryId = resultSet.getInt("countryId");
                String countryName = resultSet.getString("country");
                Address address = new Address(customer.getAddressId(), address_line1, address_line2, cityId, postalCode, phone);
                City city = new City(cityId, cityName, countryId);
                Country country = new Country(countryId, countryName);
                detailedCustomer = new DetailedCustomer(customer, address, city, country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detailedCustomer;
    }


}
