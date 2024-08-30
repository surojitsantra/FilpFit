package com.flipfit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Slot {
    private int id;
    private LocalTime startTime;
    private LocalTime endTime;
    private WorkOut workOut;
    private List<Seat> seats = new ArrayList<>();
}
