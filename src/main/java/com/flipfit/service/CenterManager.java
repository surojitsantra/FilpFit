package com.flipfit.service;

import com.flipfit.model.Center;
import com.flipfit.model.CityName;

import java.util.HashMap;
import java.util.Map;

public class CenterManager {
    private static int centerId = 1;
    private Map<Integer, Center> centers = new HashMap<>();

    public Center addCenter(CityName cityName, String centerName) {
        if(centers.containsKey(centerName)) {
            System.out.println("Center already exists");
            return centers.get(centerName);
        }

        Center center = new Center();
        center.setId(centerId++);
        center.setName(centerName);
        center.setCityName(cityName);
        centers.put(center.getId(), center);

        return center;
    }

    public Center getCenter(int centerId) {
        return centers.get(centerId);
    }
}
