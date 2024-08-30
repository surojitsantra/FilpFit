package com.flipfit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    private int id;
    private int userId;
    private int centerId;
    private int slotId;
    private int seatId;
    private LocalTime time;
    private DayOfWeek dayOfWeek;
    private BookingStatus status;

}
