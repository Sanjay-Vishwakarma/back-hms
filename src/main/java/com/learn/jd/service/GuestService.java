package com.learn.jd.service;

import com.learn.jd.dto.GuestRequest;
import com.learn.jd.dto.GuestResponse;
import com.learn.jd.entity.Guest;
import com.learn.jd.exception.BusinessException;
import com.learn.jd.exception.ResourceNotFoundException;
import com.learn.jd.repository.GuestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestService {

    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public List<GuestResponse> findAll() {
        return guestRepository.findAll()
                .stream()
                .map(GuestResponse::new)
                .collect(Collectors.toList());
    }

    public GuestResponse create(GuestRequest request) {
        guestRepository.findByEmail(request.getEmail())
                .ifPresent(guest -> {
                    throw new BusinessException("Guest email already exists: " + guest.getEmail());
                });

        Guest guest = new Guest(request.getFullName(), request.getEmail(), request.getPhone(), request.getIdProof());
        return new GuestResponse(guestRepository.save(guest));
    }

    public GuestResponse update(String id, GuestRequest request) {
        Guest guest = getGuest(id);
        guestRepository.findByEmail(request.getEmail())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new BusinessException("Guest email already exists: " + existing.getEmail());
                });

        guest.setFullName(request.getFullName());
        guest.setEmail(request.getEmail());
        guest.setPhone(request.getPhone());
        guest.setIdProof(request.getIdProof());
        return new GuestResponse(guestRepository.save(guest));
    }

    public Guest getGuest(String id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id " + id));
    }
}
