package com.learn.jd.controller;

import com.learn.jd.dto.BookingRequest;
import com.learn.jd.dto.BookingResponse;
import com.learn.jd.dto.RevenueSummaryResponse;
import com.learn.jd.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<BookingResponse> findAll() {
        return bookingService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse create(@Valid @RequestBody BookingRequest request) {
        return bookingService.create(request);
    }

    @PostMapping("/{id}/check-in")
    public BookingResponse checkIn(@PathVariable Long id) {
        return bookingService.checkIn(id);
    }

    @PostMapping("/{id}/check-out")
    public BookingResponse checkOut(@PathVariable Long id) {
        return bookingService.checkOut(id);
    }

    @PostMapping("/{id}/cancel")
    public BookingResponse cancel(@PathVariable Long id) {
        return bookingService.cancel(id);
    }

    @GetMapping("/summary")
    public RevenueSummaryResponse revenueSummary() {
        return bookingService.revenueSummary();
    }
}
