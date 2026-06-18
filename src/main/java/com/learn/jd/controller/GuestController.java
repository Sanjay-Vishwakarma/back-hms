package com.learn.jd.controller;

import com.learn.jd.dto.GuestRequest;
import com.learn.jd.dto.GuestResponse;
import com.learn.jd.service.GuestService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    public List<GuestResponse> findAll() {
        return guestService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GuestResponse create(@Valid @RequestBody GuestRequest request) {
        return guestService.create(request);
    }

    @PutMapping("/{id}")
    public GuestResponse update(@PathVariable String id, @Valid @RequestBody GuestRequest request) {
        return guestService.update(id, request);
    }
}
