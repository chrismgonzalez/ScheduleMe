package DAO;

import DataModels.City;
import Utils.DbConnection;
import Utils.UserSettings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CityDaoImp implements CityDao{
    @Override
    public ObservableList<City> getAllCities() {
        ObservableList<City> allCities = FXCollections.observableArrayList();
        String query = "select cityId, city, countryId from city";
        try {
            ResultSet resultSet = DbConnection.getInstance().connection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                Integer cityId = resultSet.getInt("cityId");
                String cityName = resultSet.getString("city");
                Integer countryId = resultSet.getInt("countryId");
                City city = new City(cityId, cityName, countryId);
                allCities.add(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCities;
    }

    @Override
    public City getCityByNameAndCountryId(String cityName, int countryId) {
        City city = null;
        String query = "select cityId, city, countryId from city where city=? and countryId=?";
        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            ps.setString(1, cityName);
            ps.setInt(2, countryId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                Integer cityId = resultSet.getInt("cityId");
                city = new City(cityId, cityName, countryId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public boolean deleteCity(City city) {
        String query = "delete from city where cityId = ?";
        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            ps.setInt(1, city.getCityId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public City addCity(String cityName, int countryId) {
        City city = null;
        String query = "insert into city (cityId, city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                "values(?, ?, ?, now(), ?, now(), ?)";
        try {
            PreparedStatement ps = DbConnection.getInstance().connection().prepareStatement(query);
            int cityId = getNextPrimaryKey();
            ps.setInt(1, cityId);
            ps.setString(2, cityName);
            ps.setInt(3, countryId);
            ps.setString(4, UserSettings.userName);
            ps.setString(5, UserSettings.userName);
            ps.executeUpdate();
            city = new City(cityId, cityName, countryId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }

    private int getNextPrimaryKey() {
        int nextPk = -1;
        String query = "select max(cityId) from city";
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
