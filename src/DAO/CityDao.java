package DAO;

import DataModels.City;
import javafx.collections.ObservableList;

public interface CityDao {
    ObservableList<City> getAllCities();
    City getCityByNameAndCountryId(String cityName, int countryId);
    boolean deleteCity(City city);
    City addCity(String cityName, int countryId);
}
