package com.learn.jd.dto;

import com.learn.jd.entity.Booking;
import com.learn.jd.entity.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookingResponse {

    private Long id;
    private Long roomId;
    private String roomNumber;
    private Long guestId;
    private String guestName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfGuests;
    private BigDecimal totalAmount;
    private BookingStatus status;

    public BookingResponse(Booking booking) {
        this.id = booking.getId();
        this.roomId = booking.getRoom().getId();
        this.roomNumber = booking.getRoom().getRoomNumber();
        this.guestId = booking.getGuest().getId();
        this.guestName = booking.getGuest().getFullName();
        this.checkInDate = booking.getCheckInDate();
        this.checkOutDate = booking.getCheckOutDate();
        this.numberOfGuests = booking.getNumberOfGuests();
        this.totalAmount = booking.getTotalAmount();
        this.status = booking.getStatus();
    }

    public Long getId() {
        return id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public Long getGuestId() {
        return guestId;
    }

    public String getGuestName() {
        return guestName;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BookingStatus getStatus() {
        return status;
    }
}
