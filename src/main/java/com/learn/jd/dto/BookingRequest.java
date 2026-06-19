package com.learn.jd.dto;

import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class BookingRequest {

    @NotNull
    private String roomId;

    @NotNull
    private String guestId;

    @NotNull
    @FutureOrPresent
    private LocalDate checkInDate;

    @NotNull
    @Future
    private LocalDate checkOutDate;

    @Min(1)
    private int numberOfGuests;

}
