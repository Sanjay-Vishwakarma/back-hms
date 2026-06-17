package com.learn.jd.service;

import com.learn.jd.dto.RoomRequest;
import com.learn.jd.dto.RoomResponse;
import com.learn.jd.entity.Room;
import com.learn.jd.entity.RoomType;
import com.learn.jd.exception.BusinessException;
import com.learn.jd.exception.ResourceNotFoundException;
import com.learn.jd.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final BookingService bookingService;

    public RoomService(RoomRepository roomRepository, BookingService bookingService) {
        this.roomRepository = roomRepository;
        this.bookingService = bookingService;
    }

    public List<RoomResponse> findAll() {
        return roomRepository.findAll()
                .stream()
                .map(RoomResponse::new)
                .collect(Collectors.toList());
    }

    public List<RoomResponse> findAvailable(LocalDate checkInDate, LocalDate checkOutDate, Optional<RoomType> type) {
        List<Room> rooms = type.map(roomRepository::findByTypeAndActiveTrue)
                .orElseGet(roomRepository::findByActiveTrue);

        return rooms.stream()
                .filter(room -> bookingService.isRoomAvailable(room.getId(), checkInDate, checkOutDate))
                .map(RoomResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public RoomResponse create(RoomRequest request) {
        roomRepository.findByRoomNumber(request.getRoomNumber())
                .ifPresent(room -> {
                    throw new BusinessException("Room number already exists: " + room.getRoomNumber());
                });

        Room room = new Room(request.getRoomNumber(), request.getType(), request.getCapacity(), request.getPricePerNight());
        return new RoomResponse(roomRepository.save(room));
    }

    @Transactional
    public RoomResponse update(Long id, RoomRequest request) {
        Room room = getRoom(id);
        roomRepository.findByRoomNumber(request.getRoomNumber())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new BusinessException("Room number already exists: " + existing.getRoomNumber());
                });

        room.setRoomNumber(request.getRoomNumber());
        room.setType(request.getType());
        room.setCapacity(request.getCapacity());
        room.setPricePerNight(request.getPricePerNight());
        return new RoomResponse(room);
    }

    @Transactional
    public void deactivate(Long id) {
        Room room = getRoom(id);
        room.setActive(false);
    }

    public Room getRoom(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + id));
    }
}
