package com.flipfit.controller;

import com.flipfit.model.*;
import com.flipfit.service.BookingManager;
import com.flipfit.service.GymManager;
import com.flipfit.service.UserManager;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static java.time.DayOfWeek.FRIDAY;

public class FlipFitDriver {

    private BookingManager bookingManager;
    private GymManager gymManager;
    private UserManager userManager;

    public static void main(String[] args) {
        FlipFitDriver flipFitDriver = new FlipFitDriver();

        flipFitDriver.init();
        flipFitDriver.drive();

    }

    private void init() {
        gymManager = new GymManager();
        userManager = new UserManager();
        bookingManager = new BookingManager(gymManager, userManager);
    }

    private void drive() {

        User john = userManager.addUser("John");
        User jane = userManager.addUser("Jane");

        Country india = gymManager.addCountry(CountryName.INDIA);
        gymManager.addCity(CountryName.INDIA, CityName.BANGALORE);
        Center koramangalaCenter = gymManager.addCenter(CityName.BANGALORE, "Koramangala");
        gymManager.addSlots(koramangalaCenter, FRIDAY, LocalTime.of(10,0), WorkOut.CARDIO, 1);

        gymManager.addCity(CountryName.INDIA, CityName.KOLKATA);
        Center saltLakeCenter = gymManager.addCenter(CityName.KOLKATA, "Salt Lake");
        gymManager.addSlots(saltLakeCenter, FRIDAY, LocalTime.of(10,0), WorkOut.DANCE, 2);
        gymManager.addSlots(saltLakeCenter, FRIDAY, LocalTime.of(11,0), WorkOut.CARDIO, 2);

        Center newTownCenter = gymManager.addCenter(CityName.KOLKATA, "New Town");
        gymManager.addSlots(newTownCenter, FRIDAY, LocalTime.of(10,0), WorkOut.YOGA, 2);
        gymManager.addSlots(newTownCenter, FRIDAY, LocalTime.of(11,0), WorkOut.AEROBICS, 2);

        System.out.println("Country : " + india);

        List<Slot> slots = gymManager.getAvailableSlot(koramangalaCenter.getId(), FRIDAY);
        System.out.println("Available slots : " + slots);

        Booking johnBooking = bookingManager.bookSlot(john.getId(), koramangalaCenter.getId(), FRIDAY, LocalTime.of(10, 0));
        System.out.println("John booked slot : " + johnBooking);

        Booking johnDuplicateBooking = bookingManager.bookSlot(john.getId(), koramangalaCenter.getId(), FRIDAY, LocalTime.of(10, 0));
        System.out.println("John duplicate booking : " + johnDuplicateBooking);

        Booking johnCancleBooking = bookingManager.cancelBooking(johnBooking.getId());
        System.out.println("John cancel booking : " + johnCancleBooking);

        Set<Booking> johnBookings = bookingManager.getUserBookings(john.getId());
        System.out.println("John all bookings : " + johnBookings);

        System.out.println("Available slots : " + gymManager.getAvailableSlot(koramangalaCenter.getId(), FRIDAY));

        Booking janeBooking = bookingManager.bookSlot(jane.getId(), koramangalaCenter.getId(), FRIDAY, LocalTime.of(10, 0));
        System.out.println("Jane slot booking : " + janeBooking);

        System.out.println("Available slots : " + gymManager.getAvailableSlot(koramangalaCenter.getId(), FRIDAY));

    }


}
