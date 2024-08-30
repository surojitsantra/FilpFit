package com.flipfit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Center {
    private int id;
    private String name;
    private Map<DayOfWeek, Map<LocalTime, Slot>> slots = new HashMap<>();
    private CityName cityName;
}
