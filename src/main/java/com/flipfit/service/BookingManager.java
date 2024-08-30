package com.flipfit.service;

import com.flipfit.model.*;
import lombok.Synchronized;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

public class BookingManager {

    private static int bookingId = 1;
    private GymManager gymManager;
    private UserManager userManager;
    private Map<Integer, Booking> bookings = new HashMap<>();
    private Map<User, Set<Booking>> userBookings = new HashMap<>();

    public BookingManager(GymManager gymManager, UserManager userManager) {
        this.gymManager = gymManager;
        this.userManager = userManager;
    }

    @Synchronized
    public Booking bookSlot(int userId, int centerId, DayOfWeek day, LocalTime time) {

        Center center = gymManager.getCenter(centerId);
        if (center == null) {
            System.out.println("Center does not exist");
            return null;
        }

        Map<LocalTime, Slot> slots = center.getSlots().getOrDefault(day, null);

        if (slots == null) {
            System.out.println("Center is closed on this day");
            return null;
        }

        Slot slot = slots.getOrDefault(time, null);

        if (slot == null) {
            System.out.println("Slot is not available");
            return null;
        }

        Seat seat = getAvailableSeat(slot);
        if (seat == null) {
            System.out.println("Seat is not available");
            return null;
        }

        //check duplicate booking
        Optional<Booking> duplicateBooking = getUserBookings(userId).stream().filter(booking -> booking.getCenterId() == centerId && booking.getDayOfWeek() == day && booking.getTime().equals(time) && booking.getStatus() == BookingStatus.BOOKED).findFirst();

        if (duplicateBooking.isPresent()) {
            System.out.println("Duplicate booking");
            return null;
        }

        User user = userManager.getUserById(userId);
        if (user == null) {
            System.out.println("User does not exist");
            return null;
        }

        seat.setStatus(SeatStatus.BOOKED);

        Booking booking = new Booking(bookingId++, userId, centerId, slot.getId(), seat.getId(), time, day, BookingStatus.BOOKED);
        bookings.put(booking.getId(), booking);

        userBookings.putIfAbsent(user, new HashSet<>());
        userBookings.get(user).add(booking);

        return booking;
    }

    public synchronized Booking cancelBooking(int bookingId) {
        Booking booking = bookings.getOrDefault(bookingId, null);
        if (booking == null) {
            System.out.println("Invalid booking id");
            return null;
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookings.remove(bookingId);

        Center center = gymManager.getCenter(booking.getCenterId());
        Map<LocalTime, Slot> slots = center.getSlots().get(DayOfWeek.of(booking.getDayOfWeek().getValue()));

        Slot slot = slots.get(booking.getTime());
        Seat seat = getSeatById(slot, booking.getSeatId());

        if (seat == null) {
            System.out.println("Invalid seat id");
            return null;
        }

        seat.setStatus(SeatStatus.AVAILABLE);

        return booking;
    }

    private Seat getSeatById(Slot slot, int seatId) {
        return slot.getSeats().stream().filter(s -> s.getId() == seatId).findFirst().orElse(null);
    }

    private Seat getAvailableSeat(Slot slot) {
        List<Seat> seats = slot.getSeats();
        for (Seat seat : seats) {
            if (seat.getStatus() == SeatStatus.AVAILABLE) {
                return seat;
            }
        }
        return null;
    }

    public Set<Booking> getUserBookings(int userId) {
        User user = userManager.getUserById(userId);
        if (user == null) {
            System.out.println("User does not exist");
            return new HashSet<>();
        }
        return userBookings.getOrDefault(user, new HashSet<>());
    }
}
