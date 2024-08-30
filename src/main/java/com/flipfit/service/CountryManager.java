package com.flipfit.service;

import com.flipfit.model.Country;
import com.flipfit.model.CountryName;

import java.util.HashMap;
import java.util.Map;

public class CountryManager {
    private Map<CountryName, Country> countries = new HashMap<>();

    public Country addCountry(CountryName name) {
        if(countries.containsKey(name)) {
            return countries.get(name);
        }

        Country country = new Country();
        country.setName(name);
        countries.put(name, country);
        return country;
    }

    public Country getCountry(CountryName name) {
        return countries.get(name);
    }
}
