package DAO;

import DataModels.Country;
import Utils.DbConnection;
import Utils.UserSettings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDaoImp implements CountryDao {
    @Override
    public ObservableList<Country> getAllCountries() {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        String query = "select countryId, country from country";

        try {
            ResultSet resultSet = DbConnection.getInstance().connection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                Integer countryId = resultSet.getInt("countryId");
                String countryName = resultSet.getString("country");
                Country country = new Country(countryId, countryName);
                allCountries.add(country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCountries;
    }

    @Override
    public Country getCountryByName(String countryName) {
        Country country = null;
        String query = "select countryId from country where country = ?";

        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            ps.setString(1, countryName);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                Integer countryId = resultSet.getInt("countryId");
                country = new Country(countryId, countryName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }
    public boolean deleteCountry(Country country) {
        String query = "delete from country where countryId = ?";
        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            ps.setInt(1, country.getCountryId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Country addCountry(String countryName) {
        Country country = null;
        String query = "insert into country (countryId, country, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                "values(?, ?, now(), ?, now(), ?)";
        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            int countryId = getNextPrimaryKey();
            ps.setInt(1, countryId);
            ps.setString(2, countryName);
            ps.setString(3, UserSettings.userName);
            ps.setString(4, UserSettings.userName);
            ps.executeUpdate();
            country = new Country(countryId, countryName);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getSQLState());
            System.out.println(e.getErrorCode());
        }
        return country;
    }

    private int getNextPrimaryKey() {
        int nextPk = -1;
        String query = "select max(countryId) from country";
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
