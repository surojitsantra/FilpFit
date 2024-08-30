package com.flipfit.service;

import com.flipfit.model.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.flipfit.model.SeatStatus.AVAILABLE;

public class GymManager {

    private final CountryManager countryManager = new CountryManager();
    private final CityManager cityManager = new CityManager();
    private final CenterManager centerManager = new CenterManager();

    private static int slotId = 1;
    private static int seatId = 1;

    public Country addCountry(CountryName name) {
        return countryManager.addCountry(name);
    }

    public Country getCountry(CountryName name) {
        return countryManager.getCountry(name);
    }

    public City addCity(CountryName countryName, CityName cityName) {

        if(countryManager.getCountry(countryName) == null) {
            System.out.println("Country does not exist");
            return null;
        }

        City city = cityManager.addCity(countryName, cityName);

        countryManager.getCountry(countryName).getCities().add(city);
        return city;
    }

    public Center addCenter(CityName cityName, String centerName) {

        City city = cityManager.getCity(cityName);
        if(city == null) {
            System.out.println("City does not exist");
            return null;
        }

        Center center = centerManager.addCenter(cityName, centerName);
        city.getCenters().add(center);

        return center;
    }

    public Slot addSlots(Center center, DayOfWeek day, LocalTime time, WorkOut workOut, int noOfSeats) {

        if(!center.getSlots().containsKey(day)) {
            center.getSlots().put(day, new HashMap<>());
        }

        Map<LocalTime, Slot> localTimeSlotMap = center.getSlots().get(day);

        if(localTimeSlotMap.containsKey(time)) {
            return localTimeSlotMap.get(time);
        }

        Slot slot = new Slot();
        slot.setId(slotId++);
        slot.setStartTime(time);
        slot.setEndTime(time.plusHours(1));
        slot.setWorkOut(workOut);
        List<Seat> seats = new ArrayList<>();

        for(int i = 0; i < noOfSeats; i++) {
            Seat seat = new Seat(seatId++, AVAILABLE);
            seats.add(seat);
        }

        slot.setSeats(seats);

        center.getSlots().get(day).put(time, slot);

        return slot;
    }

    public Center getCenter(int centerId) {
        return centerManager.getCenter(centerId);
    }

    public List<Slot> getAvailableSlot(int centerId, DayOfWeek dayOfWeek) {

        Center center = centerManager.getCenter(centerId);
        if(center == null) {
            System.out.println("Center does not exist");
            return new ArrayList<>();
        }

        if(!center.getSlots().containsKey(dayOfWeek)) {
            System.out.println("No slots available for the day");
            return new ArrayList<>();
        }

        return center.getSlots().get(dayOfWeek).values().stream().filter(slot -> slot.getSeats().stream().anyMatch(seat -> seat.getStatus() == AVAILABLE)).collect(Collectors.toList());

    }
}
