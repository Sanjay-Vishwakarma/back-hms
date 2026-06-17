package com.learn.jd.dto;

import com.learn.jd.entity.Room;
import com.learn.jd.entity.RoomType;

import java.math.BigDecimal;

public class RoomResponse {

    private Long id;
    private String roomNumber;
    private RoomType type;
    private int capacity;
    private BigDecimal pricePerNight;
    private boolean active;

    public RoomResponse(Room room) {
        this.id = room.getId();
        this.roomNumber = room.getRoomNumber();
        this.type = room.getType();
        this.capacity = room.getCapacity();
        this.pricePerNight = room.getPricePerNight();
        this.active = room.isActive();
    }

    public Long getId() {
        return id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public boolean isActive() {
        return active;
    }
}
