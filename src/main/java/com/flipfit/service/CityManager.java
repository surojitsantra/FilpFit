package com.flipfit.service;

import com.flipfit.model.City;
import com.flipfit.model.CityName;
import com.flipfit.model.CountryName;

import java.util.HashMap;
import java.util.Map;

public class CityManager {
    private Map<CityName, City> cities = new HashMap<>();

    public City addCity(CountryName countryName, CityName cityName) {
        if(cities.containsKey(cityName)) {
            System.out.println("City already exists");
            return cities.get(cityName);
        }

        City city = new City();
        city.setName(cityName);
        city.setCountryName(countryName);
        cities.put(cityName, city);

        return city;
    }

    public City getCity(CityName cityName) {
        return cities.get(cityName);
    }
}
