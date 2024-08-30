package com.flipfit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {
    private CityName name;
    private List<Center> centers = new ArrayList<>();
    private CountryName countryName;
}
