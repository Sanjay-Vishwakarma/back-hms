package com.learn.jd.service;

import com.learn.jd.dto.BookingRequest;
import com.learn.jd.dto.BookingResponse;
import com.learn.jd.dto.RevenueSummaryResponse;
import com.learn.jd.entity.Booking;
import com.learn.jd.entity.BookingStatus;
import com.learn.jd.entity.Guest;
import com.learn.jd.entity.Room;
import com.learn.jd.exception.BusinessException;
import com.learn.jd.exception.ResourceNotFoundException;
import com.learn.jd.repository.BookingRepository;
import com.learn.jd.repository.GuestRepository;
import com.learn.jd.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private static final List<BookingStatus> OCCUPYING_STATUSES = Arrays.asList(BookingStatus.RESERVED, BookingStatus.CHECKED_IN);

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;

    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository, GuestRepository guestRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> findAll() {
        return bookingRepository.findAll()
                .stream()
                .map(BookingResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookingResponse create(BookingRequest request) {
        if (!request.getCheckOutDate().isAfter(request.getCheckInDate())) {
            throw new BusinessException("Check-out date must be after check-in date");
        }

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + request.getRoomId()));
        Guest guest = guestRepository.findById(request.getGuestId())
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id " + request.getGuestId()));

        if (!room.isActive()) {
            throw new BusinessException("Room is not active");
        }
        if (request.getNumberOfGuests() > room.getCapacity()) {
            throw new BusinessException("Number of guests exceeds room capacity");
        }
        if (!isRoomAvailable(room.getId(), request.getCheckInDate(), request.getCheckOutDate())) {
            throw new BusinessException("Room is not available for selected dates");
        }

        BigDecimal total = calculateTotal(room, request.getCheckInDate(), request.getCheckOutDate());
        Booking booking = new Booking(room, guest, request.getCheckInDate(), request.getCheckOutDate(), request.getNumberOfGuests(), total);
        return new BookingResponse(bookingRepository.save(booking));
    }

    @Transactional
    public BookingResponse checkIn(Long id) {
        Booking booking = getBooking(id);
        if (booking.getStatus() != BookingStatus.RESERVED) {
            throw new BusinessException("Only reserved bookings can be checked in");
        }
        booking.setStatus(BookingStatus.CHECKED_IN);
        return new BookingResponse(booking);
    }

    @Transactional
    public BookingResponse checkOut(Long id) {
        Booking booking = getBooking(id);
        if (booking.getStatus() != BookingStatus.CHECKED_IN) {
            throw new BusinessException("Only checked-in bookings can be checked out");
        }
        booking.setStatus(BookingStatus.CHECKED_OUT);
        return new BookingResponse(booking);
    }

    @Transactional
    public BookingResponse cancel(Long id) {
        Booking booking = getBooking(id);
        if (booking.getStatus() == BookingStatus.CHECKED_OUT) {
            throw new BusinessException("Checked-out bookings cannot be cancelled");
        }
        booking.setStatus(BookingStatus.CANCELLED);
        return new BookingResponse(booking);
    }

    @Transactional(readOnly = true)
    public boolean isRoomAvailable(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        return bookingRepository.findOverlappingBookings(roomId, checkInDate, checkOutDate, OCCUPYING_STATUSES).isEmpty();
    }

    @Transactional(readOnly = true)
    public RevenueSummaryResponse revenueSummary() {
        List<Booking> bookings = bookingRepository.findAll();
        long activeBookings = bookings.stream()
                .filter(booking -> booking.getStatus() == BookingStatus.RESERVED || booking.getStatus() == BookingStatus.CHECKED_IN)
                .count();
        long cancelledBookings = bookings.stream()
                .filter(booking -> booking.getStatus() == BookingStatus.CANCELLED)
                .count();
        BigDecimal confirmedRevenue = bookings.stream()
                .filter(booking -> booking.getStatus() != BookingStatus.CANCELLED)
                .map(Booking::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new RevenueSummaryResponse(bookings.size(), activeBookings, cancelledBookings, confirmedRevenue);
    }

    private Booking getBooking(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + id));
    }

    private BigDecimal calculateTotal(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return room.getPricePerNight().multiply(BigDecimal.valueOf(nights));
    }
}
